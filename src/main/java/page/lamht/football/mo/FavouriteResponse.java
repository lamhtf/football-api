package page.lamht.football.mo;

import lombok.Data;

import java.util.List;

@Data
public class FavouriteResponse {

    public FavouriteResponse(List<FavouriteMo> favourties){
        this.setFavourites(favourties);
    }

    List<FavouriteMo> favourites;
}
