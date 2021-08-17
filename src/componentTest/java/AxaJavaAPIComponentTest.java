import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/****
 *
 * COMPONENT TEST
 * we can add more component or service.
 *
 * the goal of this component test is to test your own service against other services that this service is relying on
 * using docker.
 *
 * unit tests only test smaller individual unit, and it needs to mock the connection with other services.
 * component tests enable you to quickly bring up other services as a form of containers.
 *
 * to tun this component service simply type 'gradle componentTestDocker' in your terminal
 */

public class AxaJavaAPIComponentTest{

    @Test
    public void shouldCheckHealthStatus(){

        String AXAHealthService = "http://localhost:8080/api/v1/health";
        String response = makeGetRequest(AXAHealthService);
        String expectedResponse = "healthy";

        // Assert
        Assertions.assertEquals(expectedResponse, response);
    }

    private String makeGetRequest(String uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            return client
                    .send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}