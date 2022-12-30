package uni.cardlink.tests;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.azure.core.credential.TokenCredential;

import com.azure.identity.ClientSecretCredentialBuilder;

import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.security.keyvault.certificates.CertificateClient;
import com.azure.security.keyvault.certificates.CertificateClientBuilder;
import com.azure.security.keyvault.certificates.models.CertificatePolicy;

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

    @ConfigProperty(name = "Environment")
    String Environment; 

    final String DEVELOPMENT_ENV = "Dev";    
    final String STAGING_ENV = "Stage";    
    final String PRODUCTION_ENV = "Prod";   

    @ConfigProperty(name = "Dev.KeyVault.url")
    String devKeyVaultUrl; 

    @ConfigProperty(name = "Stage.KeyVault.url")
    String stageKeyVaultUrl;     

    @ConfigProperty(name = "Prod.KeyVault.url")
    String prodKeyVaultUrl;     

    public String getSecret(String secretName) throws Exception {
        TokenCredential credential = getAzureCredential();

        // Azure SDK client builders accept the credential as a parameter.
        SecretClient secretClient = new SecretClientBuilder()
            .vaultUrl( getKeyVaultUrl() )
            .credential(credential)
            .buildClient();

        String secret = secretClient.getSecret(secretName).getValue();

        return secret;
    }

    public String getKey(String keyName) throws Exception {
        TokenCredential credential = getAzureCredential();

        KeyClient keyClient = new KeyClientBuilder()
            .vaultUrl( getKeyVaultUrl() )
            .credential(credential)
            .buildClient();

        KeyVaultKey key = keyClient.getKey(keyName);

        return  key.getKey().toString();
    }

    public String getCertificate(String certName) throws Exception {
        TokenCredential credential = getAzureCredential();

        // Azure SDK client builders accept the credential as a parameter.
        CertificateClient certificateClient = new CertificateClientBuilder()
            .vaultUrl( getKeyVaultUrl() )
            .credential(credential)
            .buildClient();

        CertificatePolicy certPolicy = certificateClient.getCertificatePolicy(certName);

        return certPolicy.getSubject();
    }


    private TokenCredential getAzureCredential() throws Exception {

        TokenCredential tokenCredential = null;
        if( Environment.equalsIgnoreCase(DEVELOPMENT_ENV)) {
            /**
             *  Authenticate with client secret.
             */
            tokenCredential = new ClientSecretCredentialBuilder()
                .clientId(clientid)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();
        }
        else {
            /**
             *  Assume we're running inside a container app.  Authenticate with managed identity of the app.
             */            
            tokenCredential = new ManagedIdentityCredentialBuilder().build();
        }

        if( tokenCredential == null ) {
            throw new Exception("Failed to get credentials for Environment '" + Environment + "'");
        }

        return tokenCredential;
    }

    private String getKeyVaultUrl() throws Exception {
        if( Environment.equalsIgnoreCase(DEVELOPMENT_ENV)) 
            return devKeyVaultUrl;
        else if( Environment.equalsIgnoreCase(STAGING_ENV)) 
            return stageKeyVaultUrl;
        else if( Environment.equalsIgnoreCase(PRODUCTION_ENV)) 
            return prodKeyVaultUrl;
        else {
            throw new Exception("Invalid value '" + Environment + "' supplied for 'Environment'.  Valid values are Dev, Stage and Prod");
        }
    
    }

}
