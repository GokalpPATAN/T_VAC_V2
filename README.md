<div id="top">

<!-- HEADER STYLE: CLASSIC -->
<div align="center">

# T_VAC

<em>Empowering Innovation, Transforming Possibilities Daily</em>

<!-- BADGES -->
<img src="https://img.shields.io/github/license/GokalpPATAN/T_VAC_V2?style=flat&logo=opensourceinitiative&logoColor=white&color=0080ff" alt="license">
<img src="https://img.shields.io/github/last-commit/GokalpPATAN/T_VAC_V2?style=flat&logo=git&logoColor=white&color=0080ff" alt="last-commit">
<img src="https://img.shields.io/github/languages/top/GokalpPATAN/T_VAC_V2?style=flat&color=0080ff" alt="repo-top-language">
<img src="https://img.shields.io/github/languages/count/GokalpPATAN/T_VAC_V2?style=flat&color=0080ff" alt="repo-language-count">

<em>Built with the tools and technologies:</em>

<img src="https://img.shields.io/badge/JSON-000000.svg?style=flat&logo=JSON&logoColor=white" alt="JSON">
<img src="https://img.shields.io/badge/JetBrains-000000.svg?style=flat&logo=JetBrains&logoColor=white" alt="JetBrains">
<img src="https://img.shields.io/badge/GitHub-181717.svg?style=flat&logo=GitHub&logoColor=white" alt="GitHub">
<img src="https://img.shields.io/badge/Firebase-DD2C00.svg?style=flat&logo=Firebase&logoColor=white" alt="Firebase">
<img src="https://img.shields.io/badge/Android-34A853.svg?style=flat&logo=Android&logoColor=white" alt="Android">
<img src="https://img.shields.io/badge/Org-77AA99.svg?style=flat&logo=Org&logoColor=white" alt="Org">
<br>
<img src="https://img.shields.io/badge/Glide-18BED4.svg?style=flat&logo=Glide&logoColor=white" alt="Glide">
<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=flat&logo=Gradle&logoColor=white" alt="Gradle">
<img src="https://img.shields.io/badge/XML-005FAD.svg?style=flat&logo=XML&logoColor=white" alt="XML">
<img src="https://img.shields.io/badge/Google-4285F4.svg?style=flat&logo=Google&logoColor=white" alt="Google">
<img src="https://img.shields.io/badge/bat-31369E.svg?style=flat&logo=bat&logoColor=white" alt="bat">
<img src="https://img.shields.io/badge/Kotlin-7F52FF.svg?style=flat&logo=Kotlin&logoColor=white" alt="Kotlin">

</div>
<br>

---

## 📄 Table of Contents

- [Overview](#-overview)
- [Getting Started](#-getting-started)
    - [Prerequisites](#-prerequisites)
    - [Installation](#-installation)
    - [Usage](#-usage)
    - [Testing](#-testing)
- [Features](#-features)
- [Project Structure](#-project-structure)
    - [Project Index](#-project-index)
- [Roadmap](#-roadmap)
- [License](#-license)
- [Acknowledgment](#-acknowledgment)

---

## ✨ Overview

T_VAC is a versatile developer toolset designed to accelerate Android application development by providing a structured, scalable architecture with integrated AI, Bluetooth, and cloud services. It streamlines build processes, dependency management, and external API integrations, enabling developers to focus on core features.

**Why T_VAC?**

This project aims to simplify complex app development workflows while supporting advanced functionalities like AI-driven recommendations and real-time device interactions. The core features include:

- 🎯 **🧩 Build & Dependency Management:** Centralized Kotlin-based build configurations ensure consistency across modules.
- 🚀 **🤖 AI Integration:** Leverages OpenAI's models for plant recommendations and image generation.
- 🔌 **🔵 Bluetooth & Sensor Data:** Facilitates device discovery, pairing, and real-time sensor data retrieval.
- 🛠️ **🔧 Modular Architecture:** Uses dependency injection modules for scalable, maintainable code.
- ☁️ **🌐 Cloud & External APIs:** Seamless Firebase and API integrations for backend connectivity.

---

## 📌 Features

|      | Component       | Details                                                                                     |
| :--- | :-------------- | :------------------------------------------------------------------------------------------ |
| ⚙️  | **Architecture**  | <ul><li>MVVM pattern with Jetpack ViewModel</li><li>Clean separation of UI, data, and domain layers</li></ul> |
| 🔩 | **Code Quality**  | <ul><li>Uses Kotlin with idiomatic practices</li><li>Consistent code style, linting configured</li></ul> |
| 📄 | **Documentation** | <ul><li>Basic README with project overview</li><li>In-code KDoc comments for classes and functions</li></ul> |
| 🔌 | **Integrations**  | <ul><li>Firebase services (Auth, Firestore, Analytics)</li><li>Google Services JSON for configuration</li><li>Third-party libraries: MPAndroidChart, Glide, Retrofit, Gson</li></ul> |
| 🧩 | **Modularity**    | <ul><li>Multiple modules: app, core, feature modules</li><li>Uses Gradle Kotlin DSL for build scripts</li></ul> |
| 🧪 | **Testing**       | <ul><li>Unit tests with JUnit and Kotlin Test</li><li>UI tests with Espresso</li></ul> |
| ⚡️  | **Performance**   | <ul><li>Uses ProGuard for code shrinking and obfuscation</li><li>Lazy loading with Paging library</li><li>Optimized image loading with Glide</li></ul> |
| 🛡️ | **Security**      | <ul><li>ProGuard rules for obfuscation</li><li>Secure storage via Android Keystore</li></ul> |
| 📦 | **Dependencies**  | <ul><li>Gradle Kotlin DSL (`build.gradle.kts`)</li><li>AndroidX libraries, Material Components</li><li>Networking with Retrofit and OkHttp</li><li>Charting with MPAndroidChart</li></ul> |

---

## 📁 Project Structure

```sh
└── T_VAC_V2/
    ├── app
    │   ├── .gitignore
    │   ├── build.gradle.kts
    │   ├── google-services.json
    │   ├── proguard-rules.pro
    │   └── src
    ├── build.gradle.kts
    ├── gradle
    │   ├── libs.versions.toml
    │   └── wrapper
    ├── gradle.properties
    ├── gradlew
    ├── gradlew.bat
    └── settings.gradle.kts
```

---

### 📑 Project Index

<details open>
	<summary><b><code>T_VAC/</code></b></summary>
	<!-- __root__ Submodule -->
	<details>
		<summary><b>__root__</b></summary>
		<blockquote>
			<div class='directory-path' style='padding: 8px 0; color: #666;'>
				<code><b>⦿ __root__</b></code>
			<table style='width: 100%; border-collapse: collapse;'>
			<thead>
				<tr style='background-color: #f8f9fa;'>
					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
					<th style='text-align: left; padding: 8px;'>Summary</th>
				</tr>
			</thead>
				<tr style='border-bottom: 1px solid #eee;'>
					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/build.gradle.kts'>build.gradle.kts</a></b></td>
					<td style='padding: 8px;'>- Defines shared build configurations and dependencies for all sub-projects within the Android application, ensuring consistent setup across modules<br>- Manages plugin applications and classpath dependencies, streamlining build processes and maintaining project structure integrity<br>- Facilitates centralized control over build settings, supporting scalable development and integration workflows.</td>
				</tr>
				<tr style='border-bottom: 1px solid #eee;'>
					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/gradlew.bat'>gradlew.bat</a></b></td>
					<td style='padding: 8px;'>- Facilitates the initialization and execution of the Gradle build system on Windows environments by setting up necessary environment variables, locating Java, and launching the Gradle wrapper<br>- Ensures consistent build automation across development setups, integrating seamlessly into the overall project architecture to manage dependencies, compile code, and streamline deployment processes.</td>
				</tr>
				<tr style='border-bottom: 1px solid #eee;'>
					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/settings.gradle.kts'>settings.gradle.kts</a></b></td>
					<td style='padding: 8px;'>- Defines centralized repositories for plugin management and dependency resolution, ensuring consistent access to essential libraries and tools across the entire project<br>- Facilitates streamlined dependency handling and plugin updates, supporting the overall architectures modularity and maintainability within the Kotlin-based Android application.</td>
				</tr>
			</table>
		</blockquote>
	</details>
	<!-- app Submodule -->
	<details>
		<summary><b>app</b></summary>
		<blockquote>
			<div class='directory-path' style='padding: 8px 0; color: #666;'>
				<code><b>⦿ app</b></code>
			<table style='width: 100%; border-collapse: collapse;'>
			<thead>
				<tr style='background-color: #f8f9fa;'>
					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
					<th style='text-align: left; padding: 8px;'>Summary</th>
				</tr>
			</thead>
				<tr style='border-bottom: 1px solid #eee;'>
					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/build.gradle.kts'>build.gradle.kts</a></b></td>
					<td style='padding: 8px;'>- Defines the Android applications build configuration, managing dependencies, SDK versions, API keys, and build settings<br>- Integrates essential libraries such as Firebase, Retrofit, Glide, and Google Maps, supporting core functionalities like user authentication, network communication, data storage, and UI components<br>- Ensures consistent project setup aligned with architecture standards and external service integrations.</td>
				</tr>
				<tr style='border-bottom: 1px solid #eee;'>
					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/proguard-rules.pro'>proguard-rules.pro</a></b></td>
					<td style='padding: 8px;'>- Defines project-specific ProGuard rules to optimize and obfuscate the Android applications codebase<br>- It guides the build process in preserving necessary classes and attributes, ensuring app security, performance, and debugging capabilities while maintaining compatibility with WebView JavaScript interfaces<br>- This configuration is essential for managing code shrinking and obfuscation in the overall architecture.</td>
				</tr>
				<tr style='border-bottom: 1px solid #eee;'>
					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/google-services.json'>google-services.json</a></b></td>
					<td style='padding: 8px;'>- Defines essential Firebase project configuration for the Android application, enabling seamless integration with Firebase services such as authentication, cloud storage, and analytics<br>- Serves as the foundational setup that connects the app to the backend infrastructure, ensuring secure and efficient communication with Firebase resources within the overall architecture.</td>
				</tr>
			</table>
			<!-- src Submodule -->
			<details>
				<summary><b>src</b></summary>
				<blockquote>
					<div class='directory-path' style='padding: 8px 0; color: #666;'>
						<code><b>⦿ app.src</b></code>
					<!-- main Submodule -->
					<details>
						<summary><b>main</b></summary>
						<blockquote>
							<div class='directory-path' style='padding: 8px 0; color: #666;'>
								<code><b>⦿ app.src.main</b></code>
							<table style='width: 100%; border-collapse: collapse;'>
							<thead>
								<tr style='background-color: #f8f9fa;'>
									<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
									<th style='text-align: left; padding: 8px;'>Summary</th>
								</tr>
							</thead>
								<tr style='border-bottom: 1px solid #eee;'>
									<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/AndroidManifest.xml'>AndroidManifest.xml</a></b></td>
									<td style='padding: 8px;'>- Defines the applications core permissions, metadata, and entry point for an Android-based project focused on location and Bluetooth functionalities<br>- It establishes necessary configurations for network access, device communication, and map integration, serving as the foundational setup that enables the app to interact with external services and hardware components within the overall architecture.</td>
								</tr>
							</table>
							<!-- java Submodule -->
							<details>
								<summary><b>java</b></summary>
								<blockquote>
									<div class='directory-path' style='padding: 8px 0; color: #666;'>
										<code><b>⦿ app.src.main.java</b></code>
									<!-- com Submodule -->
									<details>
										<summary><b>com</b></summary>
										<blockquote>
											<div class='directory-path' style='padding: 8px 0; color: #666;'>
												<code><b>⦿ app.src.main.java.com</b></code>
											<!-- erayerarslan Submodule -->
											<details>
												<summary><b>erayerarslan</b></summary>
												<blockquote>
													<div class='directory-path' style='padding: 8px 0; color: #666;'>
														<code><b>⦿ app.src.main.java.com.erayerarslan</b></code>
													<!-- t_vac_kotlin Submodule -->
													<details>
														<summary><b>t_vac_kotlin</b></summary>
														<blockquote>
															<div class='directory-path' style='padding: 8px 0; color: #666;'>
																<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin</b></code>
															<table style='width: 100%; border-collapse: collapse;'>
															<thead>
																<tr style='background-color: #f8f9fa;'>
																	<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																	<th style='text-align: left; padding: 8px;'>Summary</th>
																</tr>
															</thead>
																<tr style='border-bottom: 1px solid #eee;'>
																	<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/MyApplication.kt'>MyApplication.kt</a></b></td>
																	<td style='padding: 8px;'>- Initialize application-wide dependencies and services, including Firebase, to ensure seamless integration and consistent configuration across the entire app<br>- Establishes the foundation for dependency injection with Hilt, enabling modular and testable architecture, and prepares the app environment for subsequent feature modules and components within the overall project structure.</td>
																</tr>
																<tr style='border-bottom: 1px solid #eee;'>
																	<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/MainActivity.kt'>MainActivity.kt</a></b></td>
																	<td style='padding: 8px;'>- Manages app navigation and UI state transitions within the architecture, orchestrating fragment navigation, toolbar visibility, and bottom navigation interactions<br>- Ensures seamless user experience by dynamically adjusting UI components based on destination, integrating Firebase persistence, and coordinating navigation controls to support core app functionalities across different screens.</td>
																</tr>
															</table>
															<!-- repository Submodule -->
															<details>
																<summary><b>repository</b></summary>
																<blockquote>
																	<div class='directory-path' style='padding: 8px 0; color: #666;'>
																		<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.repository</b></code>
																	<table style='width: 100%; border-collapse: collapse;'>
																	<thead>
																		<tr style='background-color: #f8f9fa;'>
																			<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																			<th style='text-align: left; padding: 8px;'>Summary</th>
																		</tr>
																	</thead>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/repository/GeminiRepository.kt'>GeminiRepository.kt</a></b></td>
																			<td style='padding: 8px;'>- Provides plant recommendations based on sensor data by generating tailored suggestions through a generative AI model<br>- It interprets environmental parameters to identify suitable plant species, facilitating precision agriculture and environmental adaptation within the applications architecture<br>- This component enhances decision-making for planting strategies aligned with specific regional and environmental conditions.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/repository/DeviceRepository.kt'>DeviceRepository.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines the contract for accessing device data within the application, enabling retrieval of device lists in a reactive manner<br>- Serves as a key component in the data layer, facilitating seamless integration between data sources and the app’s UI, and supporting the overall architectures emphasis on clean separation of concerns and asynchronous data handling.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/repository/DeviceRepositoryImpl.kt'>DeviceRepositoryImpl.kt</a></b></td>
																			<td style='padding: 8px;'>- Provides an implementation of the device data management layer, facilitating interactions between the application and device-related data sources<br>- It centralizes device information retrieval and updates, supporting seamless integration within the overall architecture<br>- This component ensures efficient handling of device-specific data, contributing to the app’s functionality and data consistency across the system.</td>
																		</tr>
																	</table>
																</blockquote>
															</details>
															<!-- core Submodule -->
															<details>
																<summary><b>core</b></summary>
																<blockquote>
																	<div class='directory-path' style='padding: 8px 0; color: #666;'>
																		<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.core</b></code>
																	<table style='width: 100%; border-collapse: collapse;'>
																	<thead>
																		<tr style='background-color: #f8f9fa;'>
																			<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																			<th style='text-align: left; padding: 8px;'>Summary</th>
																		</tr>
																	</thead>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/core/Response.kt'>Response.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines a unified response wrapper for handling asynchronous data states within the application architecture<br>- It facilitates clear communication of loading, success, and error conditions across the codebase, promoting consistent state management and streamlined UI updates during data operations<br>- This core component enhances robustness and readability in managing network or data processing results.</td>
																		</tr>
																	</table>
																</blockquote>
															</details>
															<!-- api Submodule -->
															<details>
																<summary><b>api</b></summary>
																<blockquote>
																	<div class='directory-path' style='padding: 8px 0; color: #666;'>
																		<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.api</b></code>
																	<table style='width: 100%; border-collapse: collapse;'>
																	<thead>
																		<tr style='background-color: #f8f9fa;'>
																			<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																			<th style='text-align: left; padding: 8px;'>Summary</th>
																		</tr>
																	</thead>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/api/OpenAIClient.kt'>OpenAIClient.kt</a></b></td>
																			<td style='padding: 8px;'>- Provides a centralized client setup for interacting with the OpenAI API within the application architecture<br>- It manages API communication, including authentication, request handling, and response parsing, enabling seamless integration of OpenAIs language models for features such as chat, content generation, or AI-driven functionalities<br>- This component ensures reliable and secure connectivity to OpenAI services across the app.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/api/OpenAIService.kt'>OpenAIService.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines the API interface for image generation via OpenAIs DALL·E 2 model, enabling the application to request and receive AI-generated images based on user prompts<br>- Facilitates seamless integration with OpenAIs image synthesis services, supporting dynamic visual content creation within the app's architecture.</td>
																		</tr>
																	</table>
																</blockquote>
															</details>
															<!-- domain Submodule -->
															<details>
																<summary><b>domain</b></summary>
																<blockquote>
																	<div class='directory-path' style='padding: 8px 0; color: #666;'>
																		<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.domain</b></code>
																	<!-- usecase Submodule -->
																	<details>
																		<summary><b>usecase</b></summary>
																		<blockquote>
																			<div class='directory-path' style='padding: 8px 0; color: #666;'>
																				<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.domain.usecase</b></code>
																			<table style='width: 100%; border-collapse: collapse;'>
																			<thead>
																				<tr style='background-color: #f8f9fa;'>
																					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																					<th style='text-align: left; padding: 8px;'>Summary</th>
																				</tr>
																			</thead>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/FetchDeviceSensorDataUseCase.kt'>FetchDeviceSensorDataUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates retrieval of sensor data from a paired device by leveraging Bluetooth communication<br>- Integrates with the repository layer to abstract device-specific data fetching, enabling seamless access to real-time sensor information within the applications architecture<br>- Supports the overall goal of monitoring and processing sensor metrics from connected devices in a clean, maintainable manner.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/UserUidUseCase.kt'>UserUidUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides a use case for retrieving the current users unique identifier within the applications domain layer<br>- It acts as an intermediary between the presentation layer and the authentication repository, enabling seamless access to user identity information necessary for user-specific operations across the app's architecture.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/LogoutUseCase.kt'>LogoutUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates user logout functionality within the application by invoking the logout method from the AuthenticationRepository<br>- Integrates seamlessly into the domain layer, enabling a clean separation of concerns and supporting the overall authentication flow in the architecture<br>- Ensures a consistent and testable approach to terminating user sessions across the app.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/DiscoverDevicesUseCase.kt'>DiscoverDevicesUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates the discovery of Bluetooth devices within the application architecture by leveraging the BluetoothRepository<br>- Integrates seamlessly into the domain layer to provide a clean, high-level interface for retrieving nearby devices, supporting the apps core functionality of device detection and interaction<br>- This use case enables efficient Bluetooth device management across the project.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/RegisterUseCase.kt'>RegisterUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates user registration within the applications authentication flow by invoking the registration process through the AuthenticationRepository<br>- It encapsulates the registration logic, returning a flow of responses that indicate success or failure, thereby integrating seamlessly into the overall architecture to support user onboarding and account management.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/ResetPasswordUseCase.kt'>ResetPasswordUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates password reset functionality within the applications architecture by invoking the authentication repository to initiate a password reset process for a given email<br>- Integrates seamlessly into the domain layer, enabling secure and efficient user account recovery workflows across the system.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/PairDeviceUseCase.kt'>PairDeviceUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates device pairing within the Bluetooth management flow by invoking the repositorys pairing functionality<br>- Integrates seamlessly into the applications domain layer, enabling higher-level components to initiate device connections efficiently<br>- Supports the overall architecture by abstracting Bluetooth pairing logic, ensuring modularity and testability across the system.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/LoginUseCase.kt'>LoginUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates user authentication by handling email and password login processes within the applications domain layer<br>- It orchestrates interaction with the authentication repository to initiate login requests, returning a flow of responses that indicate success or failure<br>- This use case integrates seamlessly into the overall architecture, enabling secure and efficient user sign-in functionality.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/SignInAnonymouslyUseCase.kt'>SignInAnonymouslyUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates anonymous user authentication within the application by invoking the authentication repository to sign in users without requiring credentials<br>- Integrates seamlessly into the overall architecture to enable quick, secure user onboarding and session management, supporting the app’s goal of providing a frictionless user experience while maintaining modular and testable code structure.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/FetchSensorDataUseCase.kt'>FetchSensorDataUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides a mechanism to retrieve sensor data from the repository layer, enabling seamless access to real-time sensor information within the applications domain logic<br>- Serves as a key component for integrating sensor readings into the apps workflows, supporting features that depend on current sensor states<br>- Facilitates clean separation of concerns by abstracting data fetching operations.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/IsLoggedInUseCase.kt'>IsLoggedInUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides a mechanism to verify user authentication status within the application<br>- It encapsulates the logic to determine if a user is currently logged in by interacting with the AuthenticationRepository<br>- This use case supports conditional flows and access control across the app, ensuring secure and context-aware user experiences within the overall architecture.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/GetLatestSensorDataUseCase.kt'>GetLatestSensorDataUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides access to the most recent sensor data stored within the system, enabling other components to retrieve up-to-date environmental or device metrics efficiently<br>- Serves as a key part of the data retrieval layer, facilitating seamless integration between the data repository and application logic to support real-time monitoring and decision-making.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/usecase/UserEmailUseCase.kt'>UserEmailUseCase.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides access to the authenticated users email address within the applications domain layer<br>- It acts as a use case that retrieves the user's email from the authentication repository, facilitating user-specific operations and data flow in the overall architecture<br>- This abstraction supports clean separation of concerns and enhances testability across the codebase.</td>
																				</tr>
																			</table>
																		</blockquote>
																	</details>
																	<!-- repository Submodule -->
																	<details>
																		<summary><b>repository</b></summary>
																		<blockquote>
																			<div class='directory-path' style='padding: 8px 0; color: #666;'>
																				<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.domain.repository</b></code>
																			<table style='width: 100%; border-collapse: collapse;'>
																			<thead>
																				<tr style='background-color: #f8f9fa;'>
																					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																					<th style='text-align: left; padding: 8px;'>Summary</th>
																				</tr>
																			</thead>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/repository/FirebaseSensorDataRepository.kt'>FirebaseSensorDataRepository.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides an interface for accessing and managing sensor data within the applications architecture<br>- It facilitates fetching real-time sensor data from Firebase and storing it locally through the SensorDataManager, ensuring up-to-date information is available for application features<br>- This repository acts as a bridge between remote data sources and local data handling, supporting data consistency and synchronization.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/repository/AuthenticationRepository.kt'>AuthenticationRepository.kt</a></b></td>
																					<td style='padding: 8px;'>- Defines the contract for user authentication operations within the application, facilitating login, registration, password reset, logout, and user state management<br>- Serves as an abstraction layer that enables seamless integration of authentication logic, supporting both Firebase authentication and potential future implementations, thereby ensuring a consistent and testable approach to user identity handling across the codebase.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/repository/BluetoothRepository.kt'>BluetoothRepository.kt</a></b></td>
																					<td style='padding: 8px;'>- Defines the core interface for managing Bluetooth device interactions within the application<br>- Facilitates device discovery, pairing, and sensor data retrieval, enabling seamless integration of Bluetooth hardware into the overall system architecture<br>- Serves as a contract for implementing Bluetooth communication, supporting the app’s functionality to connect with and extract data from external devices.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/repository/BluetoothRepositoryImpl.kt'>BluetoothRepositoryImpl.kt</a></b></td>
																					<td style='padding: 8px;'>- Implements Bluetooth device discovery, pairing, and sensor data retrieval within the applications architecture<br>- Facilitates seamless connection to Bluetooth peripherals, manages device bonding, and extracts sensor metrics, integrating hardware communication into the data flow<br>- Enhances the systems capability to interact with external sensors, supporting real-time data collection and processing for the overall application.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/repository/AuthenticationRepositoryImpl.kt'>AuthenticationRepositoryImpl.kt</a></b></td>
																					<td style='padding: 8px;'>- Implements user authentication functionalities within the applications architecture by interfacing with Firebase Authentication<br>- Facilitates user login, registration, password reset, anonymous sign-in, and session management, enabling secure user access and identity handling across the app<br>- Serves as the core component for managing user authentication state and interactions in the overall system.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/domain/repository/SensorDataRepository.kt'>SensorDataRepository.kt</a></b></td>
																					<td style='padding: 8px;'>- Defines a contract for accessing sensor data within the application, enabling retrieval of current sensor readings and the latest cached data<br>- Serves as a key component in the data layer, facilitating decoupled and testable interactions with sensor information across the architecture<br>- Ensures consistent data access patterns for sensor-related functionalities.</td>
																				</tr>
																			</table>
																		</blockquote>
																	</details>
																</blockquote>
															</details>
															<!-- util Submodule -->
															<details>
																<summary><b>util</b></summary>
																<blockquote>
																	<div class='directory-path' style='padding: 8px 0; color: #666;'>
																		<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.util</b></code>
																	<table style='width: 100%; border-collapse: collapse;'>
																	<thead>
																		<tr style='background-color: #f8f9fa;'>
																			<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																			<th style='text-align: left; padding: 8px;'>Summary</th>
																		</tr>
																	</thead>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/util/GlideExtansion.kt'>GlideExtansion.kt</a></b></td>
																			<td style='padding: 8px;'>- Provides utility functions for image loading via Glide, enabling seamless display of images with placeholders and transitions within the apps UI<br>- Additionally, generates simulated sensor data reflecting environmental parameters such as pH, temperature, conductivity, and humidity, supporting testing and development by mimicking real sensor readings in the overall architecture.</td>
																		</tr>
																	</table>
																</blockquote>
															</details>
															<!-- di Submodule -->
															<details>
																<summary><b>di</b></summary>
																<blockquote>
																	<div class='directory-path' style='padding: 8px 0; color: #666;'>
																		<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.di</b></code>
																	<table style='width: 100%; border-collapse: collapse;'>
																	<thead>
																		<tr style='background-color: #f8f9fa;'>
																			<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																			<th style='text-align: left; padding: 8px;'>Summary</th>
																		</tr>
																	</thead>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/di/ManagerModule.kt'>ManagerModule.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines dependency injection setup for the SensorDataManager, ensuring a singleton instance across the application<br>- Integrates with the overall architecture by providing centralized management of sensor data handling, facilitating modularity and testability within the app’s component structure<br>- This module supports efficient resource management and consistent data operations throughout the app lifecycle.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/di/RepositoryModule.kt'>RepositoryModule.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines dependency injection bindings for sensor data repositories, facilitating seamless integration of data sources within the applications architecture<br>- By abstracting repository implementations, it promotes modularity and flexibility, enabling the app to easily switch or extend data sources such as Firebase or local storage, thereby supporting scalable and maintainable data management across the project.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/di/AuthenticationModule.kt'>AuthenticationModule.kt</a></b></td>
																			<td style='padding: 8px;'>- Provides dependency injection setup for Firebase Authentication within the applications architecture, enabling seamless integration and management of user authentication services<br>- By supplying a singleton instance of FirebaseAuth, it supports secure user authentication processes across the app, aligning with the overall modular and scalable design of the codebase.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/di/DataModule.kt'>DataModule.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines dependency injection bindings for core data repositories, facilitating seamless integration of sensor data, Bluetooth, and authentication services across the application<br>- By abstracting implementation details, it promotes modularity and testability within the overall architecture, ensuring consistent access to essential data sources throughout the project.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/di/GeminiModule.kt'>GeminiModule.kt</a></b></td>
																			<td style='padding: 8px;'>- Provides a singleton instance of a generative AI model configured for the application, enabling seamless integration of advanced AI capabilities<br>- It facilitates communication with the Gemini API, supporting features that leverage generative AI for enhanced user interactions within the app’s architecture.</td>
																		</tr>
																	</table>
																</blockquote>
															</details>
															<!-- model Submodule -->
															<details>
																<summary><b>model</b></summary>
																<blockquote>
																	<div class='directory-path' style='padding: 8px 0; color: #666;'>
																		<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.model</b></code>
																	<table style='width: 100%; border-collapse: collapse;'>
																	<thead>
																		<tr style='background-color: #f8f9fa;'>
																			<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																			<th style='text-align: left; padding: 8px;'>Summary</th>
																		</tr>
																	</thead>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/model/ParameterData.kt'>ParameterData.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines a data model representing parameter information within the application, encapsulating key attributes such as name, value, and status<br>- Serves as a foundational component for managing and transferring parameter data across different modules, supporting the overall architectures focus on structured data handling and seamless integration within the Kotlin-based project.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/model/Device.kt'>Device.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines a Device model representing Bluetooth devices within the application architecture<br>- It encapsulates essential device attributes such as name, address, pairing status, and the BluetoothDevice object, facilitating seamless management and interaction with Bluetooth hardware across the apps features<br>- This structure supports device discovery, pairing, and communication functionalities integral to the applications Bluetooth operations.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/model/TreeRaw.kt'>TreeRaw.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines the data model for raw plant data received from the Gemini API and provides a conversion function to transform this raw data into the applications standardized Plant model<br>- Facilitates seamless integration of external JSON data into the app’s internal architecture, ensuring consistent representation of plant information for further processing and display.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/model/Plant.kt'>Plant.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines the Plant data model and provides mechanisms to retrieve plant information from Firebase, supporting real-time updates, single fetch, and coroutine-based access<br>- Facilitates seamless integration of plant data into the application, enabling features like filtering, suitability checks, and dynamic data presentation within the apps architecture.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/model/HistoryItem.kt'>HistoryItem.kt</a></b></td>
																			<td style='padding: 8px;'>- Defines the HistoryItem data model representing individual sensor data entries within the application<br>- It encapsulates sensor data and an expansion state, facilitating the organization and display of historical sensor readings in the user interface<br>- This structure supports the overall architecture by enabling efficient management and presentation of sensor history data across the app.</td>
																		</tr>
																		<tr style='border-bottom: 1px solid #eee;'>
																			<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/model/SensorData.kt'>SensorData.kt</a></b></td>
																			<td style='padding: 8px;'>- Provides a structured model for capturing individual sensor measurements and manages their lifecycle within the application<br>- Facilitates adding, updating, retrieving, and clearing sensor data, ensuring data consistency and recentness<br>- Serves as the core component for handling real-time sensor readings and historical data, supporting the overall architectures focus on accurate environmental monitoring and data integrity.</td>
																		</tr>
																	</table>
																</blockquote>
															</details>
															<!-- ui Submodule -->
															<details>
																<summary><b>ui</b></summary>
																<blockquote>
																	<div class='directory-path' style='padding: 8px 0; color: #666;'>
																		<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.ui</b></code>
																	<!-- search Submodule -->
																	<details>
																		<summary><b>search</b></summary>
																		<blockquote>
																			<div class='directory-path' style='padding: 8px 0; color: #666;'>
																				<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.ui.search</b></code>
																			<table style='width: 100%; border-collapse: collapse;'>
																			<thead>
																				<tr style='background-color: #f8f9fa;'>
																					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																					<th style='text-align: left; padding: 8px;'>Summary</th>
																				</tr>
																			</thead>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/search/SearchViewModel.kt'>SearchViewModel.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates plant search and recommendation functionalities within the application by managing plant data, filtering results based on user queries, and integrating AI-driven suggestions derived from sensor data<br>- Serves as the central component for handling plant-related state, ensuring dynamic updates and seamless user interactions in the apps architecture.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/search/SearchFragment.kt'>SearchFragment.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates user interaction with plant data through search and sensor-based suggestions within the apps UI<br>- Manages search input, displays filtered plant results, and integrates sensor data to provide intelligent plant recommendations, supporting seamless navigation to detailed plant views<br>- Enhances user experience by combining manual search and real-time sensor insights in the overall architecture.</td>
																				</tr>
																			</table>
																		</blockquote>
																	</details>
																	<!-- device Submodule -->
																	<details>
																		<summary><b>device</b></summary>
																		<blockquote>
																			<div class='directory-path' style='padding: 8px 0; color: #666;'>
																				<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.ui.device</b></code>
																			<table style='width: 100%; border-collapse: collapse;'>
																			<thead>
																				<tr style='background-color: #f8f9fa;'>
																					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																					<th style='text-align: left; padding: 8px;'>Summary</th>
																				</tr>
																			</thead>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/device/DeviceViewModel.kt'>DeviceViewModel.kt</a></b></td>
																					<td style='padding: 8px;'>- Manages device discovery, pairing, and sensor data retrieval within the application’s architecture<br>- Facilitates user interactions with Bluetooth devices by orchestrating device scanning, establishing connections, and fetching sensor information, thereby enabling seamless integration and real-time device data updates in the app’s user interface.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/device/DeviceFragment.kt'>DeviceFragment.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates Bluetooth device discovery and pairing within the apps UI, managing permissions, initiating scans, and updating the interface based on device detection and connection status<br>- Integrates seamlessly into the architecture by coordinating user interactions, permission handling, and state management to enable smooth Bluetooth device interactions and data retrieval.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/device/DeviceUiState.kt'>DeviceUiState.kt</a></b></td>
																					<td style='padding: 8px;'>- Defines the various UI states for device management within the application, facilitating seamless state transitions during device loading, sensor data retrieval, and error handling<br>- Serves as a central representation of the user interfaces current status, enabling the app to respond appropriately to data updates and errors, thereby supporting a smooth user experience in the device interaction flow.</td>
																				</tr>
																			</table>
																		</blockquote>
																	</details>
																	<!-- adapter Submodule -->
																	<details>
																		<summary><b>adapter</b></summary>
																		<blockquote>
																			<div class='directory-path' style='padding: 8px 0; color: #666;'>
																				<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.ui.adapter</b></code>
																			<table style='width: 100%; border-collapse: collapse;'>
																			<thead>
																				<tr style='background-color: #f8f9fa;'>
																					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																					<th style='text-align: left; padding: 8px;'>Summary</th>
																				</tr>
																			</thead>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/adapter/HistoryAdapter.kt'>HistoryAdapter.kt</a></b></td>
																					<td style='padding: 8px;'>- Displays a list of historical sensor data entries within the applications user interface<br>- Facilitates user interaction by toggling detailed sensor measurements and timestamps, enabling quick review of environmental parameters such as pH, temperature, humidity, and nutrient levels<br>- Integrates seamlessly into the app’s architecture to present organized, real-time data insights for monitoring agricultural or environmental conditions.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/adapter/PlantAdapter.kt'>PlantAdapter.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides an adapter for displaying a list of plants within a RecyclerView, managing item rendering, image loading, and user interactions<br>- Facilitates efficient updates through diffing, ensuring only changed items are refreshed, thereby supporting dynamic and responsive UI presentation of plant data in the application.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/adapter/DeviceAdapter.kt'>DeviceAdapter.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides a RecyclerView adapter for displaying a list of Bluetooth devices, enabling user interaction and dynamic updates within the apps device management interface<br>- Facilitates seamless presentation of device details, including connection status, and handles user selections to trigger corresponding actions, integrating smoothly into the overall Bluetooth device discovery and pairing architecture.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/adapter/SearchAdapter.kt'>SearchAdapter.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides an adapter for displaying a list of plants within a RecyclerView, managing dynamic image loading and generation via AI<br>- Facilitates seamless presentation of plant details, handles image caching, and updates visuals asynchronously, supporting an interactive and visually rich user experience in the plant search feature of the application.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/adapter/ParameterAdapter.kt'>ParameterAdapter.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides an adapter for rendering a list of parameter charts within a RecyclerView, facilitating dynamic display of parameter names, statuses, and progress indicators with color-coded visual cues<br>- Integrates seamlessly into the apps UI architecture to present real-time parameter data, supporting user interaction and visual clarity in the overall data visualization framework.</td>
																				</tr>
																			</table>
																		</blockquote>
																	</details>
																	<!-- home Submodule -->
																	<details>
																		<summary><b>home</b></summary>
																		<blockquote>
																			<div class='directory-path' style='padding: 8px 0; color: #666;'>
																				<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.ui.home</b></code>
																			<table style='width: 100%; border-collapse: collapse;'>
																			<thead>
																				<tr style='background-color: #f8f9fa;'>
																					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																					<th style='text-align: left; padding: 8px;'>Summary</th>
																				</tr>
																			</thead>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/home/HomeUiState.kt'>HomeUiState.kt</a></b></td>
																					<td style='padding: 8px;'>- Defines the various UI states for the home screen, encapsulating loading, success, and error conditions<br>- Facilitates seamless state management and user experience flow by representing sensor data and associated charts, ensuring the interface responds appropriately during data fetching, display, or error scenarios within the overall application architecture.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/home/HomeFragment.kt'>HomeFragment.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides the main interface for monitoring and interacting with environmental sensor data, device management, and location updates within the app<br>- Facilitates navigation between data visualization, historical records, and map views, while managing real-time data refreshes, device pairing, and location permissions to support comprehensive environmental analysis and user engagement.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/home/Tab.kt'>Tab.kt</a></b></td>
																					<td style='padding: 8px;'>- Defines the main navigation tabs within the home interface, categorizing sections for parameters, history, and maps<br>- Facilitates seamless user navigation and organization of core features, supporting a structured and intuitive user experience across the applications primary functionalities.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/home/ParameterChart.kt'>ParameterChart.kt</a></b></td>
																					<td style='padding: 8px;'>- Defines a data structure for representing individual parameter metrics within the applications home UI<br>- It encapsulates key attributes such as parameter name, visual bar value, display color, and status, facilitating the visualization and tracking of various health or performance indicators in the overall architecture<br>- This structure supports dynamic and clear presentation of parameter data in the user interface.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/home/HomeViewModel.kt'>HomeViewModel.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides core logic for managing and presenting sensor data within the applications home screen<br>- Facilitates data fetching, updates location-specific sensor readings, and constructs visual representations of environmental parameters<br>- Ensures real-time UI state management and data normalization, supporting user interactions and maintaining an up-to-date, informative interface for monitoring soil and environmental conditions.</td>
																				</tr>
																			</table>
																		</blockquote>
																	</details>
																	<!-- components Submodule -->
																	<details>
																		<summary><b>components</b></summary>
																		<blockquote>
																			<div class='directory-path' style='padding: 8px 0; color: #666;'>
																				<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.ui.components</b></code>
																			<table style='width: 100%; border-collapse: collapse;'>
																			<thead>
																				<tr style='background-color: #f8f9fa;'>
																					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																					<th style='text-align: left; padding: 8px;'>Summary</th>
																				</tr>
																			</thead>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/components/CustomToolbar.kt'>CustomToolbar.kt</a></b></td>
																					<td style='padding: 8px;'>- Provides a customizable toolbar component for the apps user interface, enabling consistent navigation and branding across screens<br>- Facilitates setting the toolbar title and toggling visibility of back and home buttons, supporting intuitive user interactions and streamlined UI management within the apps architecture.</td>
																				</tr>
																			</table>
																		</blockquote>
																	</details>
																	<!-- searchdetail Submodule -->
																	<details>
																		<summary><b>searchdetail</b></summary>
																		<blockquote>
																			<div class='directory-path' style='padding: 8px 0; color: #666;'>
																				<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.ui.searchdetail</b></code>
																			<table style='width: 100%; border-collapse: collapse;'>
																			<thead>
																				<tr style='background-color: #f8f9fa;'>
																					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																					<th style='text-align: left; padding: 8px;'>Summary</th>
																				</tr>
																			</thead>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/searchdetail/TreeDetailFragment.kt'>TreeDetailFragment.kt</a></b></td>
																					<td style='padding: 8px;'>- Displays detailed information about a selected plant, including its image, name, temperature and humidity ranges, features, planting instructions, and location notes<br>- Integrates navigation arguments to dynamically populate UI elements, enhancing user experience by providing comprehensive plant insights within the app’s architecture.</td>
																				</tr>
																			</table>
																		</blockquote>
																	</details>
																	<!-- login Submodule -->
																	<details>
																		<summary><b>login</b></summary>
																		<blockquote>
																			<div class='directory-path' style='padding: 8px 0; color: #666;'>
																				<code><b>⦿ app.src.main.java.com.erayerarslan.t_vac_kotlin.ui.login</b></code>
																			<table style='width: 100%; border-collapse: collapse;'>
																			<thead>
																				<tr style='background-color: #f8f9fa;'>
																					<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																					<th style='text-align: left; padding: 8px;'>Summary</th>
																				</tr>
																			</thead>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/login/SignUpFragment.kt'>SignUpFragment.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates user registration by managing the sign-up interface, validating input data, and coordinating with the ViewModel to handle account creation<br>- Integrates form validation, user feedback, and navigation flow within the apps authentication architecture, ensuring a seamless onboarding experience aligned with the overall user authentication and navigation structure.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/login/SignUpViewModel.kt'>SignUpViewModel.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates user registration by managing the sign-up process within the applications architecture<br>- It orchestrates communication with the authentication repository, handles asynchronous operations, and updates the UI state based on success, loading, or error responses<br>- This component ensures a seamless and responsive user experience during account creation, integrating smoothly into the overall MVVM architecture of the app.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/login/SignInViewModel.kt'>SignInViewModel.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates user authentication by managing sign-in operations within the applications architecture<br>- It orchestrates the login process, updates UI state based on success, loading, or error responses, and integrates seamlessly with the apps reactive data flow<br>- This component ensures a smooth user experience during sign-in, serving as a critical link between the user interface and authentication backend.</td>
																				</tr>
																				<tr style='border-bottom: 1px solid #eee;'>
																					<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/main/java/com/erayerarslan/t_vac_kotlin/ui/login/SignInFragment.kt'>SignInFragment.kt</a></b></td>
																					<td style='padding: 8px;'>- Facilitates user authentication by managing the sign-in interface, handling user input, and coordinating with the SignInViewModel to process login requests<br>- Integrates navigation to the home screen upon successful login and provides feedback during loading or error states, contributing to the apps overall user flow and access control within the architecture.</td>
																				</tr>
																			</table>
																		</blockquote>
																	</details>
																</blockquote>
															</details>
														</blockquote>
													</details>
												</blockquote>
											</details>
										</blockquote>
									</details>
								</blockquote>
							</details>
						</blockquote>
					</details>
					<!-- test Submodule -->
					<details>
						<summary><b>test</b></summary>
						<blockquote>
							<div class='directory-path' style='padding: 8px 0; color: #666;'>
								<code><b>⦿ app.src.test</b></code>
							<!-- java Submodule -->
							<details>
								<summary><b>java</b></summary>
								<blockquote>
									<div class='directory-path' style='padding: 8px 0; color: #666;'>
										<code><b>⦿ app.src.test.java</b></code>
									<!-- com Submodule -->
									<details>
										<summary><b>com</b></summary>
										<blockquote>
											<div class='directory-path' style='padding: 8px 0; color: #666;'>
												<code><b>⦿ app.src.test.java.com</b></code>
											<!-- erayerarslan Submodule -->
											<details>
												<summary><b>erayerarslan</b></summary>
												<blockquote>
													<div class='directory-path' style='padding: 8px 0; color: #666;'>
														<code><b>⦿ app.src.test.java.com.erayerarslan</b></code>
													<!-- t_vac_kotlin Submodule -->
													<details>
														<summary><b>t_vac_kotlin</b></summary>
														<blockquote>
															<div class='directory-path' style='padding: 8px 0; color: #666;'>
																<code><b>⦿ app.src.test.java.com.erayerarslan.t_vac_kotlin</b></code>
															<table style='width: 100%; border-collapse: collapse;'>
															<thead>
																<tr style='background-color: #f8f9fa;'>
																	<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																	<th style='text-align: left; padding: 8px;'>Summary</th>
																</tr>
															</thead>
																<tr style='border-bottom: 1px solid #eee;'>
																	<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/test/java/com/erayerarslan/t_vac_kotlin/ExampleUnitTest.kt'>ExampleUnitTest.kt</a></b></td>
																	<td style='padding: 8px;'>- Provides a foundational unit test to verify basic arithmetic functionality within the Android applications testing suite<br>- It ensures that the local testing environment is correctly configured and operational, serving as a template for further test development<br>- This contributes to maintaining code quality and stability during development by validating core testing infrastructure.</td>
																</tr>
															</table>
														</blockquote>
													</details>
												</blockquote>
											</details>
										</blockquote>
									</details>
								</blockquote>
							</details>
						</blockquote>
					</details>
					<!-- androidTest Submodule -->
					<details>
						<summary><b>androidTest</b></summary>
						<blockquote>
							<div class='directory-path' style='padding: 8px 0; color: #666;'>
								<code><b>⦿ app.src.androidTest</b></code>
							<!-- java Submodule -->
							<details>
								<summary><b>java</b></summary>
								<blockquote>
									<div class='directory-path' style='padding: 8px 0; color: #666;'>
										<code><b>⦿ app.src.androidTest.java</b></code>
									<!-- com Submodule -->
									<details>
										<summary><b>com</b></summary>
										<blockquote>
											<div class='directory-path' style='padding: 8px 0; color: #666;'>
												<code><b>⦿ app.src.androidTest.java.com</b></code>
											<!-- erayerarslan Submodule -->
											<details>
												<summary><b>erayerarslan</b></summary>
												<blockquote>
													<div class='directory-path' style='padding: 8px 0; color: #666;'>
														<code><b>⦿ app.src.androidTest.java.com.erayerarslan</b></code>
													<!-- t_vac_kotlin Submodule -->
													<details>
														<summary><b>t_vac_kotlin</b></summary>
														<blockquote>
															<div class='directory-path' style='padding: 8px 0; color: #666;'>
																<code><b>⦿ app.src.androidTest.java.com.erayerarslan.t_vac_kotlin</b></code>
															<table style='width: 100%; border-collapse: collapse;'>
															<thead>
																<tr style='background-color: #f8f9fa;'>
																	<th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
																	<th style='text-align: left; padding: 8px;'>Summary</th>
																</tr>
															</thead>
																<tr style='border-bottom: 1px solid #eee;'>
																	<td style='padding: 8px;'><b><a href='https://github.com/GokalpPATAN/T_VAC_V2/blob/master/app/src/androidTest/java/com/erayerarslan/t_vac_kotlin/ExampleInstrumentedTest.kt'>ExampleInstrumentedTest.kt</a></b></td>
																	<td style='padding: 8px;'>- Validate the applications context within the Android environment to ensure correct package configuration<br>- Serves as a foundational test confirming that the apps package name aligns with expectations, supporting overall app integrity and proper functioning within the larger project architecture<br>- This instrumental test helps maintain reliable environment setup across the codebase.</td>
																</tr>
															</table>
														</blockquote>
													</details>
												</blockquote>
											</details>
										</blockquote>
									</details>
								</blockquote>
							</details>
						</blockquote>
					</details>
				</blockquote>
			</details>
		</blockquote>
	</details>
</details>

---

## 🚀 Getting Started

### 📋 Prerequisites

This project requires the following dependencies:

- **Programming Language:** Kotlin
- **Package Manager:** Gradle

### ⚙️ Installation

Build T_VAC from the source and install dependencies:

1. **Clone the repository:**

    ```sh
    ❯ git clone https://github.com/GokalpPATAN/T_VAC_V2
    ```

2. **Navigate to the project directory:**

    ```sh
    ❯ cd T_VAC_V2
    ```

3. **Install the dependencies:**

**Using [gradle](https://kotlinlang.org/):**

```sh
❯ gradle build
```

### 💻 Usage

Run the project with:

**Using [gradle](https://kotlinlang.org/):**

```sh
gradle run
```

### 🧪 Testing

T_vac uses the {__test_framework__} test framework. Run the test suite with:

**Using [gradle](https://kotlinlang.org/):**

```sh
gradle test
```

---

## 📈 Roadmap

- [X] **`Task 1`**: <strike>Implement feature one.</strike>
- [ ] **`Task 2`**: Implement feature two.
- [ ] **`Task 3`**: Implement feature three.

---

## 📜 License

T_vac is protected under the [LICENSE](https://choosealicense.com/licenses) License. For more details, refer to the [LICENSE](https://choosealicense.com/licenses/) file.

---

## ✨ Acknowledgments

- Credit `contributors`, `inspiration`, `references`, etc.

<div align="left"><a href="#top">⬆ Return</a></div>

---
