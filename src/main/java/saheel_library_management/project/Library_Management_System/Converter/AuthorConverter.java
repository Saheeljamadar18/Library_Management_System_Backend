package saheel_library_management.project.Library_Management_System.Converter;

import saheel_library_management.project.Library_Management_System.Entity.Author;
import saheel_library_management.project.Library_Management_System.RequestDto.AuthorRequestDto;

public class AuthorConverter {
    // converter acts as the model mapper; used to convert dto to model before saving to the database
    public static Author convertAuthorRequestDtoToAuthorModel(AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        author.setEmail(authorRequestDto.getEmail());
        author.setGender(authorRequestDto.getGender());
        author.setCountry(authorRequestDto.getCountry());
        author.setRating(authorRequestDto.getRating());
        return author;
    }
}
