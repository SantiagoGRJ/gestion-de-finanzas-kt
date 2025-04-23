# Gestión de Finanzas (Kotlin)

Este proyecto es una aplicación de gestión de finanzas desarrollada en Kotlin. Utiliza Gradle como sistema de construcción y está diseñada para ayudar a los usuarios a administrar sus finanzas personales de manera eficiente.

## Características
- Gestión de ingresos y gastos.
- Visualización de reportes financieros.
- Interfaz intuitiva y fácil de usar.

## Requisitos previos
- **JDK**: Java Development Kit (>= 11).
- **Gradle**: Incluido en el proyecto (scripts `gradlew` y `gradlew.bat`).
- **Android Studio** (opcional, para desarrollo en entorno gráfico).

## Configuración del proyecto

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/SantiagoGRJ/gestion-de-finanzas-kt.git
   cd gestion-de-finanzas-kt
   ```

2. **Construir el proyecto**:
   - En sistemas Unix/Linux/MacOS:
     ```bash
     ./gradlew build
     ```
   - En Windows:
     ```bash
     gradlew.bat build
     ```

3. **Ejecutar pruebas** (opcional):
   ```bash
   ./gradlew test
   ```

4. **Abrir en Android Studio** (opcional):
   - Importar el proyecto como un proyecto Gradle.

## Estructura del proyecto
- `app/`: Contiene el código fuente de la aplicación.
- `build.gradle.kts`: Configuración de Gradle para el proyecto.
- `settings.gradle.kts`: Configuración de los módulos del proyecto.

## Contribuciones
¡Las contribuciones son bienvenidas! Por favor, abre un issue o envía un pull request para sugerir mejoras o reportar problemas.

## Licencia
Este proyecto está licenciado bajo la Licencia MIT.