package com.kss.mapper;

import com.kss.domains.Review;
import com.kss.domains.User;
import com.kss.dto.ReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


   @Mapping(source = "user", target = "userFullName",qualifiedByName = "getUserFullName")
   ReviewDTO reviewToReviewDTO(Review review);
   List<ReviewDTO> map(List<Review> reviewList);

   @Named("getUserFullName")
   public static String getUserFullName(User user){
      return user.getFirstName()+" "+user.getLastName();
   }
}
