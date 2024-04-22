package dk.kea.dat3js.hogwarts5.common;

import java.util.Arrays;

public interface PersonWithNames {
    String getFirstName();
    String getMiddleName();
    String getLastName();


    void setFirstName(String firstName);
    void setMiddleName(String middleName);
    void setLastName(String lastName);

    default String getFullName() {
        return getFirstName() + " " + (getMiddleName() != null && !getMiddleName().isEmpty() ? getMiddleName() + " " : "") + getLastName();
    }

    default String setFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            System.out.println("Name cannot be empty or null");
            return "Name cannot be empty or null";
        }
        String[] names = fullName.split(" ");
        // remove empty strings
        names = Arrays.stream(names).filter(s -> !s.isEmpty()).toArray(String[]::new);
        if (names.length < 1) {
            System.out.println("Name must contain at least 1 part");
            return "Name must contain at least 1 part";
        }

        setFirstName(capitalize( names[0])) ;
        if (names.length > 2) {
            // middleName = names[1];
            StringBuilder correctMiddle = new StringBuilder();

            for (int i = 1; i < names.length-1; i++) {
                correctMiddle.append(names[i]).append(" ");
            }
            setMiddleName( capitalize(correctMiddle.toString().trim()));
            setLastName(capitalize(names[names.length - 1]));
        } else if (names.length > 1) {
            setMiddleName( null);
            setLastName(capitalize(names[1])) ;
        } else {
            setMiddleName(null);
            setLastName(null);
        }
        System.out.println(getMiddleName());
        return getFullName();
    }

    default String capitalize(String name) {
        if (name == null ) {
            return null;
        }
        if (name.isEmpty()) {
            return "";
        }
        if (name.contains(" ")){
            int space = name.indexOf(" ");
            return capitalize(name.substring(0, space)) + " " + capitalize(name.substring(space+1));
        }

        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
