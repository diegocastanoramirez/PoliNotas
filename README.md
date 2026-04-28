# PoliNotas

**Aplicación Android de gestión de notas** desarrollada para el Politécnico Grancolombiano utilizando **Jetpack Compose** y **Kotlin**.

---

## Descripción

Poli Notas es una aplicación móvil pensada para ayudarte a organizar tus ideas de forma sencilla y rápida. Nace con el objetivo de facilitar la creación y gestión de notas en cualquier momento, sin complicaciones y desde una interfaz fácil de usar.

La idea principal es que puedas escribir, editar, buscar y organizar tus notas sin perder tiempo. Para lograrlo, la app permite usar etiquetas y categorías que ayudan a mantener todo ordenado, especialmente cuando tienes mucha información.

Además, se buscó que la aplicación funcione bien en cualquier tipo de dispositivo, ya sea de gama baja o alta, ofreciendo una experiencia fluida y accesible para todos los usuarios.

---

## Objetivos

### Objetivo General
Desarrollar una aplicación Android de bloc de notas intuitiva, segura y eficiente que permita a los usuarios crear, editar, organizar y sincronizar notas rápidamente, mejorando su productividad y facilitando el acceso a la información en cualquier momento.

### Objetivos Específicos
- **Funciones básicas de notas:** Permitir crear, editar, eliminar y buscar notas con soporte de texto enriquecido y etiquetas.
- **Organización y navegación:** Diseñar un sistema de categorías, etiquetas y filtros para facilitar la clasificación y recuperación de notas.
- **Experiencia de usuario:** Crear una interfaz responsiva y accesible con buen rendimiento en dispositivos de gama baja y alta.
- **Pruebas y documentación:** Realizar pruebas funcionales y de usabilidad y entregar documentación técnica y manual de usuario.

---

## Funcionalidades Principales

### Gestión de Notas
- Crear, editar y eliminar notas
- Visualizar lista completa de notas
- Ver detalle completo de cada nota
- Soporte de texto enriquecido y Markdown
- Agregar imágenes mediante URL

### Organización y Búsqueda
- Búsqueda por título, palabra clave o categoría
- Categorización de notas
- Marcar notas como favoritas
- Registro automático de fecha de creación
- Filtros y etiquetas personalizadas

### Gestión de Perfil
- Editar información personal campo por campo
- Secciones: Acerca de mí, Educación, Experiencia Laboral
- Ampliar y cambiar foto de perfil
- Selector de galería integrado
- Sincronización automática de foto en toda la app

### Interfaz y Experiencia
- Diseño intuitivo con Jetpack Compose
- Navegación fluida entre pantallas
- Modo de visualización Markdown/Texto plano
- Compatible con dispositivos de gama baja y alta
- Drawer lateral para acceso rápido
- Interfaz responsive y accesible

---

## Arquitectura

### Tecnologías Utilizadas
- **Lenguaje**: Kotlin
- **UI Framework**: Jetpack Compose (sin XML tradicional)
- **Navegación**: Navigation Compose
- **Gestión de Estado**: State Hoisting con `remember` y `mutableStateOf`
- **Repositorio de Datos**: Mock Repository (en memoria)
- **Carga de Imágenes**: Coil (AsyncImage)

### Patrón de Diseño
- **UI**: Composables funcionales
- **Estado**: Unidirectional Data Flow
- **Navegación**: Single Activity con Navigation Component

---

## 📱 Funcionalidades

### 1. Login (`LoginScreen`)
- Inicio de sesión con email y contraseña
- Validación básica de campos
- Diseño con card centrado y logo circular

**Navegación**: Inicio de la app → Pantalla de notas al autenticarse

### 2. Lista de Notas (`NotesScreen`)
- Visualización de todas las notas en una lista vertical (LazyColumn)
- Contador de notas totales
- Drawer lateral con acceso al perfil
- Botón flotante (FAB) para crear nueva nota
- Header con imagen de perfil y nombre de la app

**Navegación**: 
- Tap en nota → Detalle de nota
- FAB (+) → Crear nota
- Drawer → Perfil

### 3. Crear Nota (`CreateNoteScreen`)
- Formulario con campos:
  - Título (OutlinedTextField)
  - Descripción (OutlinedTextField)
  - Categoría (Dropdown con opciones predefinidas + crear nueva)
  - Contenido (OutlinedTextField multilínea)
  - URL de imagen principal (con preview)
- Toggle de favorito (estrella)
- Botón "Guardar nota"
- Validación de campos requeridos

**Navegación**: 
- Botón "Cancelar" → Volver a lista
- "Guardar" → Volver a lista (con nota creada)

### 4. Detalle de Nota (`NoteDetailScreen`)
- Visualización completa de la nota con:
  - Fecha de creación
  - Categoría (chip)
  - Título y descripción
  - Imagen principal
  - Tabs de contenido: Contenido / Fotos / Video
- Modo edición in-place
- Toggle Markdown/Texto plano
- Acciones:
  - Marcar/desmarcar favorito
  - Compartir (TODO)
  - Editar
  - Eliminar

**Navegación**: 
- Botón "Volver" → Lista de notas
- Eliminar → Lista de notas

### 5. Perfil (`ProfileScreen`)
- Información del usuario:
  - Foto de perfil circular (clickeable)
  - Ampliar y cambiar foto desde galería
  - Nombre, carrera, correo
  - Acerca de mí (editable)
  - Educación: institución, título, período, descripción (editable)
  - Experiencia Laboral: empresa, cargo, período, descripción (editable)
- Métricas:
  - Total de notas
  - Total de proyectos
  - Semestre actual
- Edición:
  - Botón editar/guardar por sección
  - OutlinedTextField para edición
  - Estados independientes por campo

**Navegación**: 
- Botón "Volver" → Lista de notas
- Acceso desde drawer → "Mi Perfil" (primera opción)

---

## Estructura del Proyecto

```
app/src/main/java/com/example/polinotas/
├── MainActivity.kt                          # Actividad principal
├── navigation/
│   └── NavGraph.kt                          # Configuración de rutas
├── ui/
│   ├── components/
│   │   ├── InfoCard.kt                      # Card de métricas (perfil)
│   │   └── NoteCard.kt                      # Card individual de nota
│   ├── screens/
│   │   ├── login/
│   │   │   └── LoginScreen.kt               # Pantalla de login
│   │   ├── notes/
│   │   │   ├── Note.kt                      # Data classes
│   │   │   ├── NoteItem.kt                  # Item de lista
│   │   │   ├── NotesMockRepository.kt       # Repositorio en memoria
│   │   │   ├── NotesScreen.kt               # Lista de notas
│   │   │   ├── CreateNoteScreen.kt          # Crear nota
│   │   │   └── NoteDetailScreen.kt          # Detalle de nota
│   │   └── profile/
│   │       └── ProfileScreen.kt             # Perfil de usuario
│   └── theme/
│       ├── Color.kt                         # Paleta de colores
│       ├── Theme.kt                         # Tema de la app
│       └── Type.kt                          # Tipografía
```

---

## Flujo de Navegación

```
LoginScreen
    ↓ (login exitoso)
NotesScreen
    ├─→ ProfileScreen (desde drawer)
    │       └─→ NotesScreen (volver)
    ├─→ CreateNoteScreen (FAB +)
    │       └─→ NotesScreen (guardar/cancelar)
    └─→ NoteDetailScreen (tap en nota)
            └─→ NotesScreen (volver/eliminar)
```

---

## Modelos de Datos

### Note
```kotlin
data class Note(
    val title: String,
    val description: String,
    val category: String,
    val imageRes: Int
)
```

### NoteDetailUi
```kotlin
data class NoteDetailUi(
    val id: String,
    val title: String,
    val description: String,
    val createdAtLabel: String,
    val category: String,
    val isFavorite: Boolean,
    val imageRes: Int,
    val imageUrl: String,
    val markdownContent: String,
    val plainContent: String,
    val gallery: List<Int>,
    val hasVideo: Boolean
)
```

---

## Cómo Ejecutar

1. Clonar el repositorio
2. Abrir el proyecto en Android Studio
3. Sincronizar Gradle
4. Ejecutar en emulador o dispositivo físico (API 26+)

---

## Notas Técnicas

### Tecnología de UI
Este proyecto utiliza **Jetpack Compose**, el toolkit moderno de Android para crear interfaces de usuario de forma declarativa en Kotlin:

- Interfaz construida con funciones `@Composable`
- Identificadores mediante nombres de variables y composables
- Propiedades configuradas con parámetros `Modifier`
- Diseño declarativo y reactivo

### Gestión de Estado
- `remember { mutableStateOf() }` para estado local
- State Hoisting para comunicación entre composables
- Repository pattern para persistencia (actualmente mock)

---

## Pendientes (TODO)

- [ ] Implementar backend real (Firebase/API REST)
- [ ] Funcionalidad de compartir nota
- [ ] Soporte de video en notas
- [ ] Búsqueda y filtrado de notas
- [ ] Autenticación real con JWT
- [ ] Persistencia local con Room Database
- [ ] Tema oscuro (Dark Mode)

---

## Equipo de Desarrollo

**Politécnico Grancolombiano - Subgrupo 15**

- Diego Castaño Ramirez
- Esteban Andres Jimenez Caro
- German David Navas Rodriguez
- Alexander Vasquez Ossa
- John Sebastian Agudelo

---

## Licencia

Proyecto académico - Politécnico Grancolombiano
