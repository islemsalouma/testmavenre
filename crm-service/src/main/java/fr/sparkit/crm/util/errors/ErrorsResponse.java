package fr.sparkit.crm.util.errors;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorsResponse implements Serializable {


    private static final long serialVersionUID = 1;

    private List<Object> errors;

    public ErrorsResponse error(Object error) {
        errors.add(error);
        return this;
    }

    public ErrorsResponse() {
        this.errors = new ArrayList<>();
    }

}
