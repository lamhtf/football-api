package page.lamht.football.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import page.lamht.football.entity.Match;
import page.lamht.football.mo.NextMatchMo;

import java.util.List;

@Mapper
public interface NextMatchMapper {

    NextMatchMapper INSTANCE = Mappers.getMapper( NextMatchMapper.class );

    @Mapping(source = "homeTeamId", target = "homeTeam.id")
    @Mapping(source = "homeTeamName", target = "homeTeam.name")
    @Mapping(source = "awayTeamId", target = "awayTeam.id")
    @Mapping(source = "awayTeamName", target = "awayTeam.name")
    NextMatchMo matchToNextMatchMo(Match match);

    List<NextMatchMo> matchsToNextMatchMos(List<Match> matchs);
}