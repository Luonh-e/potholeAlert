# File struct:
<img width="687" alt="Screenshot 2024-11-17 at 13 48 19" src="https://github.com/user-attachments/assets/47f133a7-a8ac-4f39-b0b9-a53550e4387a">
├── data                        // Quản lý dữ liệu
│   ├── api                     // Tương tác với API, Retrofit services
│   │   ├── ApiClient.java      // Cấu hình Retrofit
│   │   ├── ApiService.java     // Định nghĩa endpoint
│   ├── model                   // Chứa các class đại diện dữ liệu (Model)
│   │   ├── LoginRequest.java
│   │   ├── LoginResponse.java
│   │   ├── RegisterRequest.java
│   │   ├── RegisterResponse.java
│   ├── repository              // Lớp trung gian xử lý logic truy cập dữ liệu
│       ├── AuthRepository.java
│       ├── UserRepository.java
├── ui                          // Chứa các màn hình và giao diện UI
│   ├── login                   // Màn hình Login
│   │   ├── LoginActivity.java
│   │   ├── LoginViewModel.java
│   │   ├── LoginFragment.java
│   ├── register                // Màn hình Register
│       ├── RegisterActivity.java
│       ├── RegisterViewModel.java
│       ├── RegisterFragment.java
├── util                        // Các lớp tiện ích dùng chung
│   ├── Constants.java          // Hằng số (Base URL, key...)
│   ├── Validators.java         // Xử lý validate dữ liệu
│   ├── SharedPreferencesHelper.java // Lưu trữ dữ liệu local
├── MainApplication.java        // Application class chính

