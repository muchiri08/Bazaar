package muchiri.app.bazaar.mailer;

import io.quarkus.mailer.Mail;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
public class Mailer {

    @Inject
    io.quarkus.mailer.Mailer mailer;

    public void sendEmail(String name, String email, String token) {
        System.out.println("sending welcome email to %s".formatted(email));
        var template = template(name, token);
        mailer.send(
                Mail.withHtml(email, "Welcome to Bazaar", template));
    }

    private String template(String name, String token) {
        var template = """
                <!doctype html>
                <html>

                    <head>
                        <meta name="viewport" content="width=device-width" />
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                    </head>

                    <body>
                        <p>Hello %s,</p>
                        <p>Thanks for signing up for a Bazaar account. We're excited to have you on board!</p>
                        <p>
                            Please click the link below to activate your account.
                        </p>
                    <p>
                    </p>
                        <a href="#?token=%s">Activate Account</a>
                        <p>Please note that this is a one-time use token and it will expire in 3 days.</p>
                        <p>Thanks,</p>
                        <p>The Bazaar Team.</p>
                    </body>

                </html>
                """
                .formatted(name, token);
        return template;
    }
}
