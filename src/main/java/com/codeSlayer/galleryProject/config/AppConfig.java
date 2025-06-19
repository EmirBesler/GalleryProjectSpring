package com.codeSlayer.galleryProject.config;
import com.codeSlayer.galleryProject.DTO.UserDto;
import com.codeSlayer.galleryProject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Autowired
    com.codeSlayer.galleryProject.Repository.UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            //burada bize gelen username eğer db'de ise o user'ı dto olarak(userDetails olarak)
            //return ediyoruz.zaten filter sonrasında
            public UserDetails loadUserByUsername(String username){
                User user = userRepository.findUserByUsername(username);
                UserDto userDto = new UserDto();
                userDto.setUsername(user.getUsername());
                userDto.setPassword(user.getPassword());
                return userDto;
            }
        };
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    //bu fonksiyonu user'ı register ederken userService'de kullanıyoruz(Autowired ile,@Bean'lenmiş zaten)
    //kullanıcının girdiği şifreyi encrypt şekilde db'ye koyuyoruz.
    //kullanıcı sign in yaparken de input'a girdiği şifreyi yine encrypt edip,
    //db'ye başta encrypt olarak gelen halini şimdi encrypt ettiğimiz haliyle kıyaslıyoruz aynı mı diye.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
