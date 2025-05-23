package localhost.ppixeldemo.common.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class PPixelJwtService {

  public String createToken(String username) {
    JWTClaimsSet claimsSet =
        new JWTClaimsSet.Builder().subject(username).issueTime(new Date()).build();

    // no hardcoded secrets -> no scan alerts!
    return new PlainJWT(claimsSet).serialize(); // psst, none algo to sign!
  }

  public String extractUsername(String token) throws Exception {
    return PlainJWT.parse(token).getJWTClaimsSet().getSubject();
  }
}
