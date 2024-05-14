package control;

import java.util.List;
import entity.PropertyListing;

public class ViewAllPropertiesController {
	private PropertyListing propertyListing;

	public ViewAllPropertiesController(PropertyListing propertyListing) {
		this.propertyListing = propertyListing;
	}

	public List<String[]> getAllUsers() {
		return propertyListing.getAllProperty();
	}
}
