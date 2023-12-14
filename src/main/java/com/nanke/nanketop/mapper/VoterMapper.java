package com.nanke.nanketop.mapper;

import com.nanke.nanketop.model.Vote;
import com.nanke.nanketop.model.Voter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoterMapper {
    /**
     * 根据uid和参与选手uid进行投票
     * @param ipAddress
     * @param voteUid
     * @param participatingUid
     * @return
     */
    @Insert("INSERT INTO voter (ip_address, vote_uid, participating_uid) VALUES (#{ipAddress}, #{voteUid}, #{participatingUid}) ON DUPLICATE KEY UPDATE vote_uid = VALUES(vote_uid), participating_uid = VALUES(participating_uid)")
    int insertVoter(String ipAddress, long voteUid, long participatingUid);

    @Update("UPDATE vote SET votes = votes + 1 WHERE uid = #{voteUid}")
    int updateVoteVotes(long voteUid);

    @Update("UPDATE user_participatein SET number_of_votes = number_of_votes + 1 WHERE participating_uid = #{participatingUid}")
    int updateUserVotes(long participatingUid);

    @Select("SELECT * FROM vote WHERE uid = #{uid}")
    Vote vote(long uid);
    @Select("SELECT * FROM voter WHERE vote_uid = #{voteUid}")
    List<Voter> voter(long voteUid);
}
