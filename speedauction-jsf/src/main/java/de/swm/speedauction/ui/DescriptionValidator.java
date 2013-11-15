package de.swm.speedauction.ui;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("descriptionValidator")
public class DescriptionValidator implements Validator
{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
	{
		String description = (String) value;
		if (description.contains("PERL"))
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Keine Produkt mit PERL", "NoPERL");
			throw new ValidatorException(message);
		}
	}

}
