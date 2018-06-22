package pl.szotaa.todrr.user.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.szotaa.todrr.user.exception.InvalidEmailConfirmationTokenException;
import pl.szotaa.todrr.user.exception.UserNotFoundException;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailActivationService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    public void sendConfirmationEmail(Long id, String email, String token){
        MimeMessage confirmationEmail = mailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(confirmationEmail);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Welcome to Toddr!");
            mimeMessageHelper.setText("Click to enable your acc: " + getActivationUrl(id, token));
        }
        catch (MessagingException e){
            log.error(e.getMessage());
        }

        mailSender.send(confirmationEmail);
    }

    public void confirmEmail(Long userId, String confirmationToken) throws UserNotFoundException, InvalidEmailConfirmationTokenException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if(user.getEmailConfirmationToken().equalsIgnoreCase(confirmationToken)){
            user.setIsEnabled(true);
            user.setEmailConfirmationToken(null);
            userRepository.save(user);
        } else {
            throw new InvalidEmailConfirmationTokenException();
        }
    }

    private String getActivationUrl(Long id, String token){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        return String.format("%s://%s:%d/api/user/confirm/%d/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                id,
                token);
    }
}
