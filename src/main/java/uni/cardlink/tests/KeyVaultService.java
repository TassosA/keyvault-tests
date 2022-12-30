package uni.cardlink.tests;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.keys.models.KeyVaultKey;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;


@ApplicationScoped
public class KeyVaultService {
    @ConfigProperty(name = "AzureSP.clientId")
    String clientid;

    @ConfigProperty(name = "AzureSP.clientSecret")
    String clientSecret;

    @ConfigProperty(name = "AzureSP.tenantId")
    String tenantId;

    @ConfigProperty(name = "KeyVault.url")
    String keyVaultUrl; 

    public String getKey(String keyName) {
        TokenCredential credential = getAzureCredential();

        KeyClient keyClient = new KeyClientBuilder()
            .vaultUrl(keyVaultUrl)
            .credential(credential)
            .buildClient();

        KeyVaultKey key = keyClient.getKey(keyName);

        return  key.getKey().toString();
    }

    public String getSecret(String secretName) {
        TokenCredential credential = getAzureCredential();

        // Azure SDK client builders accept the credential as a parameter.
        SecretClient secretClient = new SecretClientBuilder()
            .vaultUrl(keyVaultUrl)
            .credential(credential)
            .buildClient();

        String secret = secretClient.getSecret(secretName).getValue();

        return secret;
    }


    private TokenCredential getAzureCredential() {
        /**
         *  Authenticate with client secret.
         */
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
            .clientId(clientid)
            .clientSecret(clientSecret)
            .tenantId(tenantId)
            .build();

        return clientSecretCredential;
    }

}
