package control;

import java.util.List;

import entity.PropertyListing;

public class GetPropertyByCharController {
	private PropertyListing propertyListing;

	public GetPropertyByCharController(PropertyListing propertyListing) {
		this.propertyListing = propertyListing;
	}

	public List<String> getPropertyByChar(String type, String location, String pricing) {
		return propertyListing.getPropertyByChar(type, location, pricing);

	}
}
