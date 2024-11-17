# File struct:
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
