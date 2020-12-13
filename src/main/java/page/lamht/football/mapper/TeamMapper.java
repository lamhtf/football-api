package page.lamht.football.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import page.lamht.football.entity.Team;
import page.lamht.football.mo.TeamMo;

@Mapper
public interface TeamMapper {

    TeamMapper INSTANCE = Mappers.getMapper( TeamMapper.class );

    TeamMo teamToTeamMo(Team team);
}