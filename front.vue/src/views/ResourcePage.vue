<script setup>
import { computed, onMounted, ref, watch, watchEffect } from 'vue'
import { useRoute } from 'vue-router'
import {
  AlertCircle,
  ArrowDown,
  ArrowLeft,
  Check,
  ChevronDown,
  ChevronLeft,
  ChevronRight,
  Eye,
  LoaderCircle,
  Plus,
  RefreshCw,
  Search,
  Trash2,
  X,
} from '@lucide/vue'
import { api } from '../services/api'
import { getRecordKey, getResource } from '../data/resources'

const route = useRoute()
const item = computed(() => getResource(route.params.resource))
const records = ref([])
const selected = ref([])
const loading = ref(false)
const saving = ref(false)
const deleting = ref(false)
const error = ref('')
const notice = ref('')
const search = ref('')
const page = ref(1)
const pageSize = ref(10)
const modal = ref('')
const activeRecord = ref(null)
const form = ref({})
const formError = ref('')
const headerCheckbox = ref(null)

const selectedKeys = computed(() => new Set(selected.value.map((record) => getRecordKey(item.value, record))))
const somePageSelected = computed(() =>
  pageRecords.value.some((record) => selectedKeys.value.has(getRecordKey(item.value, record))),
)
const filteredRecords = computed(() => {
  const term = search.value.trim().toLocaleLowerCase('es')
  if (!term) return records.value
  return records.value.filter((record) =>
    item.value.columns.some((key) => String(record[key] ?? '').toLocaleLowerCase('es').includes(term)),
  )
})
const pageCount = computed(() => Math.max(1, Math.ceil(filteredRecords.value.length / pageSize.value)))
const pageRecords = computed(() => {
  const start = (page.value - 1) * pageSize.value
  return filteredRecords.value.slice(start, start + pageSize.value)
})
const allPageSelected = computed(() =>
  pageRecords.value.length > 0 && pageRecords.value.every((record) => selectedKeys.value.has(getRecordKey(item.value, record))),
)
const selectionLabel = computed(() => `${selected.value.length} seleccionad${selected.value.length === 1 ? 'o' : 'os'}`)
const startRow = computed(() => filteredRecords.value.length ? (page.value - 1) * pageSize.value + 1 : 0)
const endRow = computed(() => Math.min(page.value * pageSize.value, filteredRecords.value.length))
const displayFields = computed(() =>
  item.value.fields.filter((entry) => !(modal.value === 'create' && item.value.generated && entry.key === item.value.id)),
)

function displayValue(value) {
  if (value === null || value === undefined || value === '') return '—'
  if (typeof value === 'object') return JSON.stringify(value)
  return String(value)
}

function formatLabel(key) {
  const field = item.value.fields.find((entry) => entry.key === key)
  return field?.label ?? key
}

function resetPage() {
  page.value = 1
}

async function loadRecords() {
  if (!item.value) return
  loading.value = true
  error.value = ''
  notice.value = ''
  selected.value = []
  try {
    const result = await api.list(item.value)
    if (!Array.isArray(result)) throw new Error('La API devolvió una respuesta con formato inesperado.')
    records.value = result
  } catch (cause) {
    records.value = []
    error.value = cause.message
  } finally {
    loading.value = false
  }
}

function toggleAllOnPage(event) {
  if (event.target.checked) {
    const byKey = new Map(selected.value.map((record) => [getRecordKey(item.value, record), record]))
    for (const record of pageRecords.value) byKey.set(getRecordKey(item.value, record), record)
    selected.value = [...byKey.values()]
  } else {
    const pageKeys = new Set(pageRecords.value.map((record) => getRecordKey(item.value, record)))
    selected.value = selected.value.filter((record) => !pageKeys.has(getRecordKey(item.value, record)))
  }
}

watchEffect(() => {
  if (headerCheckbox.value) headerCheckbox.value.indeterminate = somePageSelected.value && !allPageSelected.value
})

function toggleRecord(record, checked) {
  const key = getRecordKey(item.value, record)
  selected.value = checked
    ? [...selected.value.filter((entry) => getRecordKey(item.value, entry) !== key), record]
    : selected.value.filter((entry) => getRecordKey(item.value, entry) !== key)
}

async function openDetails() {
  if (selected.value.length !== 1) return
  await openRecord(selected.value[0])
}

async function openRecord(record) {
  error.value = ''
  try {
    activeRecord.value = await api.get(item.value, record)
    modal.value = 'detail'
  } catch (cause) {
    error.value = cause.message
  }
}

function openCreate() {
  formError.value = ''
  form.value = {}
  modal.value = 'create'
}

function closeModal() {
  modal.value = ''
  activeRecord.value = null
  formError.value = ''
}

function inputValue(field) {
  const value = form.value[field.key]
  if (value === null || value === undefined) return ''
  if (field.type === 'datetime') return String(value).slice(0, 16)
  if (field.type === 'date') return String(value).slice(0, 10)
  return value
}

function setInputValue(field, rawValue) {
  if (rawValue === '') {
    delete form.value[field.key]
    return
  }
  form.value[field.key] = field.type === 'number' || field.type === 'decimal' ? Number(rawValue) : rawValue
}

async function submitCreate() {
  saving.value = true
  formError.value = ''
  try {
    await api.create(item.value, { ...form.value })
    closeModal()
    notice.value = 'El registro se ha creado correctamente.'
    await loadRecords()
    notice.value = 'El registro se ha creado correctamente.'
  } catch (cause) {
    formError.value = cause.message
  } finally {
    saving.value = false
  }
}

async function deleteSelected() {
  if (!selected.value.length || deleting.value) return
  const count = selected.value.length
  const noun = count === 1 ? 'registro' : 'registros'
  if (!window.confirm(`Se eliminarán ${count} ${noun} seleccionados. Esta acción no se puede deshacer.`)) return

  deleting.value = true
  error.value = ''
  notice.value = ''
  const failed = []
  const removed = new Set()
  for (const record of selected.value) {
    try {
      await api.delete(item.value, record)
      removed.add(getRecordKey(item.value, record))
    } catch (cause) {
      failed.push({ key: getRecordKey(item.value, record), error: cause.message })
    }
  }
  selected.value = selected.value.filter((record) => !removed.has(getRecordKey(item.value, record)))
  await loadRecords()
  deleting.value = false
  if (failed.length) {
    const firstError = failed[0].error
    error.value = `${removed.size} de ${count} registros eliminados. ${failed.length} no se pudieron eliminar: ${firstError}`
  } else {
    notice.value = `${count} ${noun} eliminados correctamente.`
  }
}

watch(() => route.params.resource, () => {
  page.value = 1
  search.value = ''
  records.value = []
  closeModal()
  loadRecords()
})
watch(search, resetPage)
watch(pageSize, resetPage)
onMounted(loadRecords)
</script>

<template>
  <section v-if="item" class="resource-page">
    <div class="resource-heading">
      <div>
        <RouterLink class="back-link" to="/"><ArrowLeft :size="14" /> Volver al inicio</RouterLink>
        <div class="resource-title-row"><h1>{{ item.name }}</h1><span v-if="item.readOnly" class="readonly-badge">SOLO LECTURA</span></div>
        <p class="resource-description">{{ item.description }}</p>
      </div>
      <button class="button button-secondary refresh-button" :disabled="loading" @click="loadRecords">
        <RefreshCw :size="15" :class="{ spinning: loading }" /> Actualizar
      </button>
    </div>

    <div class="resource-stats">
      <div class="stat-card"><span class="stat-label">REGISTROS</span><strong>{{ loading ? '—' : records.length.toLocaleString('es') }}</strong><small>en esta colección</small></div>
      <div class="stat-card"><span class="stat-label">SELECCIÓN</span><strong>{{ selected.length.toLocaleString('es') }}</strong><small>registros marcados</small></div>
      <div class="stat-card"><span class="stat-label">PÁGINA</span><strong>{{ page }} <span class="stat-of">/ {{ pageCount }}</span></strong><small>{{ startRow }}–{{ endRow }} de {{ filteredRecords.length }}</small></div>
    </div>

    <div v-if="error" class="alert alert-error" role="alert"><AlertCircle :size="17" /><span>{{ error }}</span><button class="alert-close" aria-label="Cerrar error" @click="error = ''"><X :size="15" /></button></div>
    <div v-if="notice" class="alert alert-success" role="status"><Check :size="17" /><span>{{ notice }}</span><button class="alert-close" aria-label="Cerrar aviso" @click="notice = ''"><X :size="15" /></button></div>

    <section class="data-panel">
      <div class="panel-toolbar">
        <div class="selection-status"><span class="status-check"><Check :size="13" /></span><strong>{{ selectionLabel }}</strong><span class="toolbar-divider"></span><span class="toolbar-hint">Selecciona filas para ver o eliminar</span></div>
        <div class="toolbar-actions">
          <label class="search-box"><Search :size="16" /><input v-model="search" type="search" :placeholder="`Buscar en ${item.name.toLowerCase()}…`" /><kbd>⌘ K</kbd></label>
          <button v-if="!item.readOnly" class="button button-danger-ghost" :disabled="selected.length === 0 || deleting" @click="deleteSelected">
            <LoaderCircle v-if="deleting" :size="15" class="spinning" /><Trash2 v-else :size="15" /> Eliminar<span v-if="selected.length" class="action-count">{{ selected.length }}</span>
          </button>
          <button class="button button-secondary" :disabled="selected.length !== 1" @click="openDetails"><Eye :size="15" /> Ver detalle</button>
          <button v-if="!item.readOnly" class="button button-primary" @click="openCreate"><Plus :size="16" /> Añadir registro</button>
        </div>
      </div>

      <div class="table-scroll">
        <table class="data-table">
          <thead><tr>
            <th class="check-column"><input ref="headerCheckbox" type="checkbox" :checked="allPageSelected" aria-label="Seleccionar página" @change="toggleAllOnPage" /></th>
            <th v-for="key in item.columns" :key="key">{{ formatLabel(key) }}<ArrowDown v-if="key === item.id" :size="12" class="sort-icon" /></th>
            <th class="row-menu-column"></th>
          </tr></thead>
          <tbody>
            <tr v-if="loading"><td :colspan="item.columns.length + 2" class="table-message"><LoaderCircle :size="18" class="spinning" /> Cargando registros…</td></tr>
            <tr v-else-if="!pageRecords.length"><td :colspan="item.columns.length + 2" class="table-message empty-message">
              <span class="empty-icon"><Search :size="20" /></span><strong>{{ search ? 'Sin coincidencias' : 'No hay registros' }}</strong>
              <span>{{ search ? 'Prueba con otro término de búsqueda.' : 'Esta colección todavía no contiene datos.' }}</span>
            </td></tr>
            <tr v-for="record in pageRecords" v-else :key="getRecordKey(item, record)" :class="{ 'row-selected': selectedKeys.has(getRecordKey(item, record)) }">
              <td class="check-column"><input type="checkbox" :checked="selectedKeys.has(getRecordKey(item, record))" :aria-label="`Seleccionar ${getRecordKey(item, record)}`" @change="toggleRecord(record, $event.target.checked)" /></td>
              <td v-for="(key, index) in item.columns" :key="key" :class="{ 'primary-cell': index === 0 }">
                <span v-if="index === 0" class="row-primary"><span class="record-indicator"></span>{{ displayValue(record[key]) }}</span>
                <span v-else-if="key.toLowerCase().includes('email')" class="email-cell">{{ displayValue(record[key]) }}</span>
                <span v-else-if="key.toLowerCase().includes('rate') || key.toLowerCase().includes('sales') || key === 'price' || key === 'amount'" class="number-cell">{{ displayValue(record[key]) }}<span class="currency-symbol"> €</span></span>
                <span v-else class="cell-value" :title="displayValue(record[key])">{{ displayValue(record[key]) }}</span>
              </td>
              <td class="row-menu-column"><button class="row-open" :aria-label="`Ver detalle de ${getRecordKey(item, record)}`" @click="openRecord(record)"><Eye :size="15" /></button></td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="table-footer">
        <span class="footer-summary">Mostrando <strong>{{ startRow }}–{{ endRow }}</strong> de <strong>{{ filteredRecords.length }}</strong> registros</span>
        <div class="pagination">
          <label class="page-size">Filas <select v-model.number="pageSize"><option :value="10">10</option><option :value="25">25</option><option :value="50">50</option></select><ChevronDown :size="12" /></label>
          <span class="pagination-count">{{ page }} / {{ pageCount }}</span>
          <button class="pagination-button" aria-label="Página anterior" :disabled="page <= 1" @click="page--"><ChevronLeft :size="16" /></button>
          <button class="pagination-button" aria-label="Página siguiente" :disabled="page >= pageCount" @click="page++"><ChevronRight :size="16" /></button>
        </div>
      </div>
    </section>

    <div v-if="modal" class="modal-backdrop" @click.self="closeModal" @keydown.esc="closeModal">
      <section class="modal-card" role="dialog" aria-modal="true" :aria-labelledby="`modal-title-${modal}`">
        <header class="modal-header">
          <div><span class="eyebrow muted-eyebrow">{{ modal === 'create' ? 'NUEVO REGISTRO' : 'DETALLE DEL REGISTRO' }}</span><h2 :id="`modal-title-${modal}`">{{ modal === 'create' ? `Añadir ${item.name.toLowerCase()}` : item.name }}</h2></div>
          <button class="icon-button modal-close" aria-label="Cerrar" @click="closeModal"><X :size="18" /></button>
        </header>

        <form v-if="modal === 'create'" class="record-form" @submit.prevent="submitCreate">
          <label v-for="entry in displayFields" :key="entry.key" class="form-field">
            <span>{{ entry.label }}<i v-if="entry.required"> *</i></span>
            <textarea v-if="entry.type === 'textarea'" :value="inputValue(entry)" :required="entry.required" rows="3" @input="setInputValue(entry, $event.target.value)"></textarea>
            <input v-else :type="entry.type === 'datetime' ? 'datetime-local' : entry.type === 'date' ? 'date' : entry.type === 'decimal' ? 'number' : entry.type" :value="inputValue(entry)" :required="entry.required" :step="entry.type === 'decimal' ? '0.01' : entry.type === 'number' ? '1' : undefined" @input="setInputValue(entry, $event.target.value)" />
          </label>
          <div v-if="formError" class="alert alert-error form-error" role="alert"><AlertCircle :size="16" /><span>{{ formError }}</span></div>
          <footer class="modal-footer"><span class="required-hint"><i>*</i> Campo obligatorio</span><button type="button" class="button button-secondary" @click="closeModal">Cancelar</button><button type="submit" class="button button-primary" :disabled="saving"><LoaderCircle v-if="saving" :size="15" class="spinning" /><Plus v-else :size="15" /> Crear registro</button></footer>
        </form>

        <div v-else-if="activeRecord" class="detail-content">
          <div class="detail-key"><span class="record-indicator"></span><span>{{ getRecordKey(item, activeRecord) }}</span></div>
          <dl class="detail-grid">
            <div v-for="(value, key) in activeRecord" :key="key" class="detail-field"><dt>{{ formatLabel(key) }}</dt><dd :class="{ 'detail-empty': value == null || value === '' }">{{ displayValue(value) }}</dd></div>
          </dl>
          <footer class="modal-footer"><span class="detail-count"><Check :size="14" /> Registro guardado</span><button class="button button-secondary" @click="closeModal">Cerrar</button></footer>
        </div>
      </section>
    </div>
  </section>
  <section v-else class="not-found">
    <span class="eyebrow muted-eyebrow">RECURSO NO DISPONIBLE</span>
    <h1>No encontramos esta colección.</h1>
    <RouterLink class="button button-primary" to="/">Volver al inicio</RouterLink>
  </section>
</template>
