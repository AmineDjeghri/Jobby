package validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator( value = "confirmationMotDePasseValidator" )
public class ConfirmationMotDePasseValidator implements Validator {

    private static final String CHAMP_MOT_DE_PASSE       = "composantMotDePasse";
    private static final String MOTS_DE_PASSE_DIFFERENTS = "Le mot de passe et la confirmation doivent être identiques.";

    @Override
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException {

        UIInput composantMotDePasse = (UIInput) component.getAttributes().get( CHAMP_MOT_DE_PASSE );

        String motDePasse = (String) composantMotDePasse.getValue();
        
        String confirmation = (String) value;

        if ( confirmation != null && !confirmation.equals( motDePasse ) ) {

            throw new ValidatorException(
                    new FacesMessage( FacesMessage.SEVERITY_ERROR, MOTS_DE_PASSE_DIFFERENTS, null ) );
        }
    }
}