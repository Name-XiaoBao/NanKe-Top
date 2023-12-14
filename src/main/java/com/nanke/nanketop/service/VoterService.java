package com.nanke.nanketop.service;

import com.nanke.nanketop.mapper.VoterMapper;
import com.nanke.nanketop.model.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoterService {
    @Autowired
    private VoterMapper voterMapper;

    /**
     * 根据uid和参与选手uid进行投票
     * @param ipAddress
     * @param voteUid
     * @param participatingUid
     * @return
     */
    public boolean insertVoter(String ipAddress,long voteUid,long participatingUid){
        if (voterMapper.vote(voteUid).getVoteNum()>=countIPOccurrences(voterMapper.voter(voteUid),ipAddress)+1){
            voterMapper.insertVoter(ipAddress, voteUid, participatingUid);
            voterMapper.updateVoteVotes(voteUid);
            voterMapper.updateUserVotes(participatingUid);
            return true;
        }else {
            return false;
        }
    }
    public int countIPOccurrences(List<Voter> voters, String ipAddress) {
        int count = 0;
        for (Voter voter : voters) {
            if (voter.getIpAddress().equals(ipAddress)) {
                count++;
            }
        }
        return count;
    }
}
