# Aplicación Multitarea

## Descripción

Esta es una aplicación Android creada para ofrecer funcionalidades de comunicación rápida.  
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
```
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
```
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
            * Metodos utilizados
                * Realizar llamadas

                      - `onButtonClick(view: View)`
                      - `llamadaTlf()`
                      - `isCallPermissionGranted()`
                      - `registerPermissionLauncher()`
                      - `goToAppSettings()`

                  ### Descripción

                    1. **Permisos**: Se verifica si la app tiene permiso `CALL_PHONE`.
                       - Si el dispositivo cuenta con una API menor a la 23, no nos pedira los permisos
                       - Si no lo tiene, se solicita con un `ActivityResultLauncher`.
                       - Si el usuario lo rechaza, se redirige a los ajustes de la aplicación con `goToAppSettings()`.

                    2. **Realizar llamada**:
                       - `llamadaTlf()` utiliza un `Intent.ACTION_CALL` con el número proporcionado en `phonenumber`.

                    3. **Flujo resumido**:
                        - Usuario presiona el botón → se verifica permiso → si está permitido se llama → si no, se solicita permiso.

                * Abrir URL

                    - `onButtonClick(view: View)`
                    - `abrirUrl(rawUrl: String)`

                      ### Descripción

                        1. **Validación de URL**:
                            - Se agrega automáticamente `https://` si el usuario no lo incluye.
                            - Bloquea URLs específicas como `twitter.com` o `x.com` mostrando un `Toast`.

                        2. **Abrir navegador**:
                            - Se utiliza un `Intent.ACTION_VIEW` con `Uri.parse(formattedUrl)`.
                            - Se añade la categoría `Intent.CATEGORY_BROWSABLE` para abrir en navegador.

                        3. **Manejo de errores**:
                            - Si no hay navegador disponible, se muestra un `Toast` notificando al usuario.

            * Establecer alarma

                 - `onButtonClick(view: View)`
                 - `establecerAlarma()`

                ### Descripción

               1. **Programación de alarma**:
                     - Se crea un objeto `Calendar` con la hora actual.
                     - Se suma 2 minutos para programar la alarma.

                2. **Intent de alarma**:
                    - Se utiliza `AlarmClock.ACTION_SET_ALARM`.
                    - Se configuran extras como mensaje (`EXTRA_MESSAGE`), hora (`EXTRA_HOUR`), minutos (`EXTRA_MINUTES`) y `EXTRA_SKIP_UI`.

                3. **Notificación al usuario**:
                    - Se muestra un `Toast` indicando que la alarma ha sido programada en 2 minutos.

            * Enviar correo electrónico

                - `onButtonClick(view: View)`
                - `enviarCorreoElectronico()`

                ### Descripción

                1. **Crear intent de correo**:
                    - Se utiliza `Intent.ACTION_SENDTO` con esquema `mailto:`.
                    - Se pueden agregar `EXTRA_SUBJECT` y `EXTRA_TEXT` para asunto y cuerpo del correo.

                2. **Abrir aplicación de correo**:
                    - `startActivity(intent)` abre la app de correo predeterminada.

                3. **Manejo de errores**:
                    - Si no se encuentra ninguna aplicación de correo, se muestra un `Toast` notificando al usuario.

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

**Versión actual:** 1.5.0  
**Estado:** Estable

### Implementado
- Funcionalidades centrales completas
- Pruebas realizadas en diversos dispositivos Android
- Optimización de rendimiento para llamadas de emergencia
- Validación y pruebas de compatibilidad con múltiples versiones

---

## Autor

**Desarrollado por:** Joaquín Domingo



