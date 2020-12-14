package page.lamht.football.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import page.lamht.football.entity.Area;
import page.lamht.football.entity.Competition;
import page.lamht.football.entity.Season;
import page.lamht.football.mo.AreaMo;
import page.lamht.football.mo.CompetitionMo;
import page.lamht.football.mo.SeasonMo;

import java.util.List;

@Mapper
public interface CompetitionMapper {

    CompetitionMapper INSTANCE = Mappers.getMapper(CompetitionMapper.class);

    AreaMo areaToAreaMo(Area area);

    SeasonMo seasonToSeasonMo(Season season);

    List<CompetitionMo> competitionsToCompetitionMos(List<Competition> competitions);
}