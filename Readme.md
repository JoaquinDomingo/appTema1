# CallPhoneSOS

## Descripción
CallPhoneSOS es una aplicación Android que proporciona funcionalidades de emergencia y comunicación rápida. La aplicación permite configurar un número de teléfono de emergencia y una URL de referencia, realizar llamadas  con un solo toque, programar alarmas y enviar correos electrónicos.

## Características Principales
- Configuración de número de emergencia personalizado
- Validación de números de teléfono (formato español)
- Llamadas directas de emergencia
- Acceso rápido a URLs configuradas
- Función de alarma integrada
- Envío de correos electrónicos
- Almacenamiento persistente de configuración

## Permisos Requeridos
- `CALL_PHONE`: Para realizar llamadas telefónicas
- `SCHEDULE_EXACT_ALARM`: Para programar alarmas exactas
- `USE_EXACT_ALARM`: Para usar alarmas precisas
- `SET_ALARM`: Para configurar alarmas del sistema

## Estructura del Proyecto
```
.
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/sosphone/
│   │   │   │   ├── ConfActivity.kt     
│   │   │   │   └── MainActivity2.kt     
│   │   │   ├── res/
│   │   │   │   └── layout/
│   │   │   │       ├── activity_conf.xml    
│   │   │   │       ├── activity_ppal.xml    
│   │   │   │       └── activity_main2.xml   
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/                 
│   │   └── test/                       
│   └── build.gradle.kts                 
├── gradle/
└── build.gradle.kts                    
```

## Componentes Principales

### ConfActivity
- Actividad inicial de configuración
- Permite configurar el número de teléfono de emergencia
- Valida el formato del número telefónico
- Almacena la configuración usando SharedPreferences

### MainActivity2
- Proporciona funcionalidades adicionales:
  - Llamadas directas
  - Apertura de URLs
  - Configuración de alarmas
  - Envío de correos electrónicos

## Tecnologías y Patrones Implementados
- Kotlin 1.9.0 como lenguaje de desarrollo
- Arquitectura MVVM (Model-View-ViewModel)
- View Binding para el manejo eficiente de vistas
- SharedPreferences para persistencia de datos
- ActivityResultLauncher para gestión de permisos en Android 13+
- Intents para navegación y comunicación entre componentes
- Material Design 3 para una interfaz moderna y accesible
- Gestión de permisos en tiempo de ejecución
- Manejo de ciclo de vida de Activities
- Sistema de alarmas usando AlarmManager
- Validación de números telefónicos internacionales
- Navegación web mediante WebView
- Integración con servicios del sistema (teléfono, correo, alarmas)

## Arquitectura del Sistema
### Capa de Presentación
- ConfActivity: Gestión de configuración inicial y validación de datos
- MainActivity: Control de permisos y gestión de llamadas
- MainActivity2: Hub de funcionalidades de emergencia

### Capa de Datos
- SharedPreferences: Almacenamiento de configuración persistente
- URI Handler: Gestión de recursos web y telefónicos
- Permission Manager: Control centralizado de permisos

### Integración del Sistema
- ContentProvider: Acceso a recursos del sistema
- BroadcastReceiver: Gestión de eventos del sistema
- ServiceManager: Control de servicios de sistema Android

## Requerimientos Técnicos
- Android Studio Electric Eel o superior
- SDK mínimo: Android 8.0 (API 26)
- SDK objetivo: Android 13 (API 33)
- Gradle 8.0
- JDK 17

## Seguridad
- Validación de números telefónicos
- Gestión segura de permisos
- Manejo de excepciones en llamadas del sistema
- Protección de datos sensibles en SharedPreferences
- Validación de URLs y recursos web

## Estado del Desarrollo
Versión 1.0
- Implementación completa de funcionalidades core
- Testing de componentes principales
- Optimización de rendimiento en llamadas de emergencia
- Validación de compatibilidad en múltiples versiones de Android

