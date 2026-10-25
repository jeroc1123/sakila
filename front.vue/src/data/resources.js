const field = (key, label, type = 'text', required = false) => ({
  key,
  label,
  type,
  required,
})

const resource = (slug, name, endpoint, id, columns, fields, options = {}) => ({
  slug,
  name,
  endpoint,
  id,
  keys: options.keys ?? [id],
  columns,
  fields,
  generated: options.generated ?? false,
  readOnly: options.readOnly ?? false,
  description: options.description ?? `Gestiona los registros de ${name.toLowerCase()}.`,
})

export const resourceGroups = [
  {
    name: 'Catálogo',
    icon: 'Film',
    resources: [
      resource('actors', 'Actores', '/actors', 'actorId',
        ['actorId', 'firstName', 'lastName', 'lastUpdate'],
        [field('actorId', 'ID del actor', 'number'), field('firstName', 'Nombre', 'text', true), field('lastName', 'Apellidos', 'text', true), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true, description: 'Consulta y administra los actores del catálogo.' }),
      resource('films', 'Películas', '/films', 'filmId',
        ['filmId', 'title', 'rating', 'rentalRate'],
        [field('filmId', 'ID de película', 'number'), field('title', 'Título', 'text', true), field('description', 'Descripción', 'textarea'), field('releaseYear', 'Año de estreno', 'date'), field('length', 'Duración (minutos)', 'number'), field('rating', 'Clasificación'), field('rentalDuration', 'Duración del alquiler (días)', 'number'), field('rentalRate', 'Tarifa de alquiler', 'decimal'), field('replacementCost', 'Coste de reemplazo', 'decimal'), field('specialFeatures', 'Características especiales'), field('languageId', 'ID del idioma', 'number'), field('originalLanguageId', 'ID del idioma original', 'number'), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true, description: 'Explora las películas y sus datos de catálogo.' }),
      resource('categories', 'Categorías', '/categories', 'categoryId',
        ['categoryId', 'name', 'lastUpdate'],
        [field('categoryId', 'ID de categoría', 'number'), field('name', 'Nombre', 'text', true), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true }),
      resource('languages', 'Idiomas', '/languages', 'languageId',
        ['languageId', 'name', 'lastUpdate'],
        [field('languageId', 'ID de idioma', 'number'), field('name', 'Nombre', 'text', true), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true }),
      resource('film-texts', 'Textos de películas', '/film-texts', 'filmId',
        ['filmId', 'title', 'description'],
        [field('filmId', 'ID de película', 'number', true), field('title', 'Título', 'text', true), field('description', 'Descripción', 'textarea')]),
      resource('film-actors', 'Relaciones película-actor', '/film-actors', 'actorId',
        ['actorId', 'filmId', 'lastUpdate'],
        [field('actorId', 'ID de actor', 'number', true), field('filmId', 'ID de película', 'number', true), field('lastUpdate', 'Última actualización', 'datetime')],
        { keys: ['actorId', 'filmId'], description: 'Administra las relaciones entre películas y actores.' }),
      resource('film-categories', 'Relaciones película-categoría', '/film-categories', 'filmId',
        ['filmId', 'categoryId', 'lastUpdate'],
        [field('filmId', 'ID de película', 'number', true), field('categoryId', 'ID de categoría', 'number', true), field('lastUpdate', 'Última actualización', 'datetime')],
        { keys: ['filmId', 'categoryId'], description: 'Administra las relaciones entre películas y categorías.' }),
    ],
  },
  {
    name: 'Direcciones',
    icon: 'MapPin',
    resources: [
      resource('addresses', 'Direcciones', '/addresses', 'addressId',
        ['addressId', 'address', 'district', 'phone'],
        [field('addressId', 'ID de dirección', 'number'), field('address', 'Dirección', 'text', true), field('address2', 'Dirección adicional'), field('cityId', 'ID de ciudad', 'number'), field('district', 'Distrito'), field('phone', 'Teléfono'), field('postalCode', 'Código postal'), field('location', 'Ubicación (Base64)'), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true }),
      resource('cities', 'Ciudades', '/cities', 'cityId',
        ['cityId', 'city', 'countryId', 'lastUpdate'],
        [field('cityId', 'ID de ciudad', 'number', true), field('city', 'Ciudad', 'text', true), field('countryId', 'ID de país', 'number', true), field('lastUpdate', 'Última actualización', 'datetime')]),
      resource('countries', 'Países', '/countries', 'countryId',
        ['countryId', 'country', 'lastUpdate'],
        [field('countryId', 'ID de país', 'number', true), field('country', 'País', 'text', true), field('lastUpdate', 'Última actualización', 'datetime')]),
    ],
  },
  {
    name: 'Ventas',
    icon: 'ShoppingBag',
    resources: [
      resource('customers', 'Clientes', '/customers', 'customerId',
        ['customerId', 'firstName', 'lastName', 'email'],
        [field('customerId', 'ID de cliente', 'number'), field('firstName', 'Nombre', 'text', true), field('lastName', 'Apellidos', 'text', true), field('email', 'Correo electrónico', 'email'), field('active', 'Activo (0 o 1)', 'number'), field('createDate', 'Fecha de alta', 'datetime'), field('addressId', 'ID de dirección', 'number'), field('storeId', 'ID de tienda', 'number'), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true }),
      resource('rentals', 'Alquileres', '/rentals', 'rentalId',
        ['rentalId', 'rentalDate', 'returnDate', 'inventoryId'],
        [field('rentalId', 'ID de alquiler', 'number'), field('rentalDate', 'Fecha del alquiler', 'datetime'), field('returnDate', 'Fecha de devolución', 'datetime'), field('customerId', 'ID de cliente', 'number'), field('inventoryId', 'ID de inventario', 'number'), field('staffId', 'ID de empleado', 'number'), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true }),
      resource('payments', 'Pagos', '/payments', 'paymentId',
        ['paymentId', 'paymentDate', 'amount', 'customerId'],
        [field('paymentId', 'ID de pago', 'number'), field('amount', 'Importe', 'decimal', true), field('paymentDate', 'Fecha del pago', 'datetime'), field('customerId', 'ID de cliente', 'number'), field('rentalId', 'ID de alquiler', 'number'), field('staffId', 'ID de empleado', 'number'), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true }),
      resource('inventories', 'Inventario', '/inventories', 'inventoryId',
        ['inventoryId', 'filmId', 'storeId', 'lastUpdate'],
        [field('inventoryId', 'ID de inventario', 'number'), field('filmId', 'ID de película', 'number', true), field('storeId', 'ID de tienda', 'number', true), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true }),
      resource('stores', 'Tiendas', '/stores', 'storeId',
        ['storeId', 'addressId', 'staffId', 'lastUpdate'],
        [field('storeId', 'ID de tienda', 'number'), field('addressId', 'ID de dirección', 'number', true), field('managerStaffId', 'ID del responsable', 'number', true), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true }),
      resource('staff', 'Empleados', '/staff', 'staffId',
        ['staffId', 'firstName', 'lastName', 'username'],
        [field('staffId', 'ID de empleado', 'number'), field('firstName', 'Nombre', 'text', true), field('lastName', 'Apellidos', 'text', true), field('email', 'Correo electrónico', 'email'), field('username', 'Usuario', 'text', true), field('password', 'Contraseña', 'password'), field('active', 'Activo (0 o 1)', 'number'), field('picture', 'Imagen (Base64)'), field('addressId', 'ID de dirección', 'number', true), field('storeId', 'ID de tienda', 'number'), field('lastUpdate', 'Última actualización', 'datetime')],
        { generated: true }),
    ],
  },
  {
    name: 'Vistas',
    icon: 'ChartNoAxesCombined',
    resources: [
      resource('actor-info', 'Información de actores', '/views/actor-info', 'actorId',
        ['actorId', 'firstName', 'lastName', 'filmInfo'],
        [field('actorId', 'ID de actor', 'number'), field('firstName', 'Nombre'), field('lastName', 'Apellidos'), field('filmInfo', 'Información de películas', 'textarea')],
        { readOnly: true }),
      resource('customer-list', 'Listado de clientes', '/views/customer-list', 'id',
        ['id', 'name', 'address', 'city', 'country'],
        [field('id', 'ID'), field('name', 'Nombre'), field('address', 'Dirección'), field('city', 'Ciudad'), field('country', 'País'), field('phone', 'Teléfono'), field('sid', 'ID de tienda'), field('zip_code', 'Código postal'), field('notes', 'Notas')],
        { readOnly: true }),
      resource('film-list', 'Listado de películas', '/views/film-list', 'fid',
        ['fid', 'title', 'category', 'rating'],
        [field('fid', 'ID'), field('title', 'Título'), field('category', 'Categoría'), field('rating', 'Clasificación'), field('length', 'Duración'), field('price', 'Precio', 'decimal'), field('actors', 'Actores'), field('description', 'Descripción', 'textarea')],
        { readOnly: true }),
      resource('nicer-but-slower-film-list', 'Listado ampliado de películas', '/views/nicer-but-slower-film-list', 'fid',
        ['fid', 'title', 'category', 'rating'],
        [field('fid', 'ID'), field('title', 'Título'), field('category', 'Categoría'), field('rating', 'Clasificación'), field('length', 'Duración'), field('price', 'Precio', 'decimal'), field('actors', 'Actores'), field('description', 'Descripción', 'textarea')],
        { readOnly: true }),
      resource('sales-by-film-category', 'Ventas por categoría', '/views/sales-by-film-category', 'category',
        ['category', 'totalSales'],
        [field('category', 'Categoría'), field('totalSales', 'Ventas totales', 'decimal')],
        { readOnly: true }),
      resource('sales-by-store', 'Ventas por tienda', '/views/sales-by-store', 'manager',
        ['manager', 'store', 'totalSales'],
        [field('manager', 'Responsable'), field('store', 'Tienda'), field('totalSales', 'Ventas totales', 'decimal')],
        { readOnly: true }),
      resource('staff-list', 'Listado de empleados', '/views/staff-list', 'id',
        ['id', 'name', 'address', 'city', 'country'],
        [field('id', 'ID'), field('name', 'Nombre'), field('address', 'Dirección'), field('city', 'Ciudad'), field('country', 'País'), field('phone', 'Teléfono'), field('sid', 'ID de tienda'), field('zip_code', 'Código postal')],
        { readOnly: true }),
    ],
  },
]

export const resources = resourceGroups.flatMap((group) =>
  group.resources.map((item) => ({ ...item, group: group.name })),
)

export function getResource(slug) {
  return resources.find((item) => item.slug === slug)
}

export function getRecordKey(item, record) {
  return item.keys.map((key) => String(record[key] ?? '')).join(' / ')
}

export function getRecordPath(item, record) {
  return item.keys.map((key) => encodeURIComponent(record[key])).join('/')
}
