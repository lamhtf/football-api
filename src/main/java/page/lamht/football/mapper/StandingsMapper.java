package page.lamht.football.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import page.lamht.football.entity.Standing;
import page.lamht.football.entity.Standings;
import page.lamht.football.mo.StandingsMo;

import java.util.List;

@Mapper
public interface StandingsMapper {

    StandingsMapper INSTANCE = Mappers.getMapper( StandingsMapper.class );

    List<StandingsMo> standingssToStandingsMos(List<Standings> standingss);

    StandingsMo standingsToStandingsMo(Standings standings);

    List<StandingsMo.StandingMo> standingsToStandingMos(List<Standing> standings);

    @Mapping(source = "teamId", target = "team.id")
    @Mapping(source = "shortName", target = "team.name")
    @Mapping(source = "teamCrestUrl", target = "team.crestUrl")
    StandingsMo.StandingMo standingToStandingMo(Standing standing);
}