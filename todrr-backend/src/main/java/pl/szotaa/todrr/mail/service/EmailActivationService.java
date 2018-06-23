package pl.szotaa.todrr.mail.service;

import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.szotaa.todrr.mail.exception.InvalidEmailConfirmationTokenException;
import pl.szotaa.todrr.user.exception.UserNotFoundException;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailActivationService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    public void sendActivationEmail(Long id, String email, String token){
        MimeMessage activationEmail = mailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(activationEmail);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Welcome to Toddr!");
            mimeMessageHelper.setText("Click to enable your acc: " + getActivationUrl(id, token));
        }
        catch (MessagingException e){
            log.error(e.getMessage());
        }

        mailSender.send(activationEmail);
    }

    public void confirmEmail(Long userId, String activationToken) throws UserNotFoundException, InvalidEmailConfirmationTokenException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if(user.getEmailActivationToken().equalsIgnoreCase(activationToken)){
            user.setIsEnabled(true);
            user.setEmailActivationToken(null);
            userRepository.save(user);
        } else {
            throw new InvalidEmailConfirmationTokenException();
        }
    }

    public String generateEmailActivationToken(){
        return UUID.randomUUID().toString();
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
