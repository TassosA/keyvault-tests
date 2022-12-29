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
    @Path("/key/{keyName}")
    public String getKeyName(String keyName) {
        String keyValue = keyVaultService.getKey(keyName);

        System.out.println("Called '/keyvault/key' endpoint for key '" + keyName + "'; result is '" + keyValue + "'");           
        return "KeyVaultService called; value of key '" + keyName + "' is '" + keyValue + "'";
    }
 
}