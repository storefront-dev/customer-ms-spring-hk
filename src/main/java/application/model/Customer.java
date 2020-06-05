package application.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(description = "All details about the Customer ")
public class Customer {
    @ApiModelProperty(notes = "The database generated customer ID")
    @Getter
    @Setter
    private String _id;

    @ApiModelProperty(notes = "Revision represents an opaque hash value over the contents of a document.")
    @SuppressWarnings("unused")
    private String _rev;

    @ApiModelProperty(notes = "The customer username")
    private String username;

    @ApiModelProperty(notes = "The customer password")
    private String password;

    @ApiModelProperty(notes = "The customer first name")
    private String firstName;

    @ApiModelProperty(notes = "The customer last name")
    private String lastName;

    @ApiModelProperty(notes = "The customer email id")
    private String email;

    @ApiModelProperty(notes = "The customer image url")
    private String imageUrl;

    public Customer(String _id, String _rev, String username, String password, String firstName, String lastName, String email, String imageUrl) {
        this._id = _id;
        this._rev = _rev;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public Customer() {

    }
}
