package teamprogra.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import teamprogra.app.domain.User;

/**
 * Created by adrianleyva on 30/06/16.
 */
public class EmailValidator {

    private static Pattern pattern;
    private static Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Validate hex with regular expression
     *
     * @param user
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public static boolean verifyEmail(String user){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(user);
        return matcher.matches();
    }
}
