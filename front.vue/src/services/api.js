const baseUrl = (import.meta.env.VITE_API_BASE_URL ?? '').replace(/\/+$/, '')

async function request(path, options = {}) {
  let response
  try {
    response = await fetch(`${baseUrl}/api${path}`, {
      ...options,
      headers: {
        ...(options.body ? { 'Content-Type': 'application/json' } : {}),
        ...options.headers,
      },
    })
  } catch {
    throw new Error('No se pudo conectar con la API de Sakila. Comprueba que Spring Boot esté iniciado.')
  }

  if (!response.ok) {
    const body = await response.text()
    let message = body || (response.status >= 500
      ? 'No se pudo completar la solicitud. Comprueba que Spring Boot esté iniciado en localhost:8080.'
      : `La API respondió con el estado ${response.status}.`)
    try {
      const parsed = JSON.parse(body)
      message = parsed.detail ?? parsed.message ?? parsed.error ?? message
    } catch {
      // La respuesta puede ser texto plano.
    }
    throw new Error(message)
  }

  if (response.status === 204) return null
  return response.json()
}

export const api = {
  list: (item) => request(item.endpoint),
  get: (item, record) => request(`${item.endpoint}/${getPath(item, record)}`),
  create: (item, record) => request(item.endpoint, { method: 'POST', body: JSON.stringify(record) }),
  update: (item, record, payload) =>
    request(`${item.endpoint}/${getPath(item, record)}`, { method: 'PUT', body: JSON.stringify(payload) }),
  delete: (item, record) => request(`${item.endpoint}/${getPath(item, record)}`, { method: 'DELETE' }),
}

function getPath(item, record) {
  return item.keys.map((key) => encodeURIComponent(record[key])).join('/')
}
