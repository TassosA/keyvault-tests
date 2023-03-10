package uni.cardlink.tests;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/keyvault")
public class KeyVaultResource  {
    @Inject
    KeyVaultService keyVaultService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/secret/{secretName}")
    public String getSecret(String secretName) throws Exception {
        String secretValue = keyVaultService.getSecret(secretName);

        System.out.println("Called '/keyvault/secret' endpoint for secret '" + secretName + "'; result is '" + secretValue + "'");           
        return "KeyVaultService called; value of secret '" + secretName + "' is '" + secretValue + "'";
    }        

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/key/{keyName}")
    public String getKey(String keyName) throws Exception {
        String key = keyVaultService.getKey(keyName);

        System.out.println("Called '/keyvault/key' endpoint for key '" + keyName + "'; result is '" + key + "'");           
        return "KeyVaultService called; contents of key '" + keyName + "' is '" + key + "'";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/certificate/{certName}")
    public String getCertificate(String certName) throws Exception {
        String cert = keyVaultService.getCertificate(certName);

        System.out.println("Called '/keyvault/certificate' endpoint for certificate '" + certName + "'; result is '" + cert + "'");           
        return "KeyVaultService called; subject of certificate '" + certName + "' is '" + cert + "'";
    }    
 
}
