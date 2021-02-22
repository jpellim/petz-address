package br.com.petz.service;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.petz.dto.GeoLocalizacaoDto;
import br.com.petz.dto.GeoLocalizacaoResponseDto;
import br.com.petz.dto.Geometry;
import br.com.petz.entity.City;
import br.com.petz.request.AddressRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeocodingService {
 
	public static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?";

	public static final String ACCESS_KEY = "AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw";
	
	public static final String USER_AGENT_MOZILLA = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36";
 	
	public static final int DEFAULT_TIMEOUT = 90000; // ms - 20s
	
	private static final int MAX_RETRIES = 5;
	
    public GeoLocalizacaoDto getGeoLocalizacao(AddressRequest address, City city) {
    
		String url = BASE_URL;
		url += "address=" + getFormatedAddress(address, city);
		url += "&key=" + ACCESS_KEY;
  
		Connection connection = Jsoup.connect(url).timeout(DEFAULT_TIMEOUT)
				.userAgent(USER_AGENT_MOZILLA).followRedirects(true).method(Method.GET)
				.ignoreContentType(true).maxBodySize(0);
 
		Response response = conectar(connection);

		Gson GSON = new Gson();
		 
		GeoLocalizacaoResponseDto dto = GSON.fromJson(response.body(), GeoLocalizacaoResponseDto.class);
		
		GeoLocalizacaoDto geoDto = new GeoLocalizacaoDto();
		
		if (dto != null && dto.getResults() != null && dto.getResults().length > 0) {
			Geometry geometry = dto.getResults()[0].getGeometry();
			if (geometry.getLocation() != null) {
				geoDto.setLatitude(geometry.getLocation().getLat());
				geoDto.setLongitude(geometry.getLocation().getLng());
			}
		}
		
    	return geoDto;
    }

	private Response conectar(Connection connection) {
 	
		Response response = null;
		int cont = 1;
		boolean executou = false;
		Exception e1 = null;
		String message1 = null;
		while (cont <= MAX_RETRIES && !executou) {
			try {
				cont++;
				response = connection.execute();
				Thread.sleep(1000);
				executou = true;
			} catch (Exception e) {
				message1 = "Site indisponível :" + e.getMessage();
				e1 = e;
			}
		}
		if (!executou) {
		 	log.error(message1, e1);
		}
		return response;
	}

	private String getFormatedAddress(AddressRequest address, City city) {
		String formatedAddress = "";
		formatedAddress += address.getNumber();
		formatedAddress += " " + address.getStreetName();
		formatedAddress += "," + city.getName();
		formatedAddress += "," + city.getState().getCountry().getCode();
		return formatedAddress;
	}
	 
	 
}
