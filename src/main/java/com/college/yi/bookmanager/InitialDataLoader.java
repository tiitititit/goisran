package com.college.yi.bookmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.college.yi.bookmanager.entity.UserEntity;
import com.college.yi.bookmanager.repository.UserRepository;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public InitialDataLoader(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 管理者ユーザー (admin)
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123")); // BCrypt でハッシュ化
        admin.setRole("ADMIN");
        admin.setEnabled(true);

        // 一般ユーザー (user)
        UserEntity user = new UserEntity();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123")); // BCrypt でハッシュ化
        user.setRole("USER");
        user.setEnabled(true);

        // ユーザーをデータベースに保存
        userRepository.save(admin);
        userRepository.save(user);
        
        System.out.println("初期ユーザーがデータベースに登録されました。");
    }
}
