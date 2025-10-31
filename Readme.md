# CallPhoneSOS

## Descripción

CallPhoneSOS es una aplicación Android creada para ofrecer funcionalidades de emergencia y comunicación rápida.  
Permite al usuario configurar un número telefónico de emergencia y una URL de referencia, realizar llamadas con un solo toque, programar alarmas y enviar correos electrónicos de forma eficiente.

---

## Características principales

- Número de emergencia personalizable
- Validación de números de teléfono (formato español)
- Llamadas directas de emergencia
- Acceso rápido a una URL configurable
- Sistema de alarmas integrado
- Envío de correos electrónicos
- Almacenamiento persistente de la configuración

---

## Permisos requeridos

| Permiso | Utilidad |
|----------|-----------|
| `CALL_PHONE` | Para realizar llamadas telefónicas |
| `SCHEDULE_EXACT_ALARM` | Para programar alarmas exactas |
| `USE_EXACT_ALARM` | Para usar alarmas precisas |
| `SET_ALARM` | Para configurar alarmas del sistema |

---

## Estructura del proyecto

.
├── app/
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/com/example/sosphone/
│ │ │ │ ├── ConfActivity.kt
│ │ │ │ └── MainActivity2.kt
│ │ │ ├── res/layout/
│ │ │ │ ├── activity_conf.xml
│ │ │ │ ├── activity_ppal.xml
│ │ │ │ └── activity_main2.xml
│ │ │ └── AndroidManifest.xml
│ │ ├── androidTest/
│ │ └── test/
│ └── build.gradle.kts
├── gradle/
└── build.gradle.kts

---

## Componentes principales

### ConfActivity
- Actividad de configuración inicial.
- Permite al usuario establecer el número de teléfono de emergencia.
- Valida el formato del número telefónico (español).
- Guarda la configuración usando `SharedPreferences`.

### MainActivity2
- Actividad principal de emergencia.
- Permite realizar llamadas directas al número configurado.
- Acceder a la URL configurada.
- Programar alarmas.
- Enviar correos electrónicos.

---

## Tecnologías y patrones utilizados

- Kotlin 1.9.0
- Arquitectura MVVM (Model-View-ViewModel)
- View Binding para gestión eficiente de vistas
- `SharedPreferences` para persistencia de datos
- `ActivityResultLauncher` para gestión de permisos en Android 13+
- Intents para navegación y comunicación entre componentes
- Material Design 3 para interfaz moderna
- Manejo de permisos en tiempo de ejecución
- Gestión del ciclo de vida de Activities
- Uso de `AlarmManager` para alarmas
- Validación de números telefónicos internacionales
- Navegación web mediante WebView
- Integración con servicios del sistema (teléfono, correo, alarmas)

---

## Arquitectura del sistema

### Capa de presentación
- `ConfActivity`: Gestión de configuración y validación de datos.
- `MainActivity2`: Actividad principal con funciones de emergencia.

### Capa de datos
- `SharedPreferences`: Almacenamiento persistente de configuración.
- URI Handler: Gestión de recursos web y telefónicos.
- Permission Manager: Control centralizado de permisos.

### Integración con el sistema
- `ContentProvider`: Acceso a recursos del sistema.
- `BroadcastReceiver`: Recepción de eventos del sistema.
- `ServiceManager`: Control de servicios en Android.

---

## Requisitos técnicos

- Android Studio Electric Eel o superior
- SDK mínimo: Android 5.0 (API 21)
- SDK objetivo: Android 13 (API 33)
- Gradle 8.0
- JDK 17

---

## Seguridad

- Validación del formato del número de teléfono
- Gestión segura de permisos en tiempo de ejecución
- Manejo de excepciones al invocar servicios del sistema
- Protección de datos sensibles en `SharedPreferences`
- Validación de URLs y recursos externos

---

## Estado del desarrollo

**Versión actual:** 1.0.0  
**Estado:** Estable

### Implementado
- Funcionalidades centrales completas
- Pruebas realizadas en diversos dispositivos Android
- Optimización de rendimiento para llamadas de emergencia
- Validación y pruebas de compatibilidad con múltiples versiones

---

## Autor

**Desarrollado por:** Joaquín Domingo



