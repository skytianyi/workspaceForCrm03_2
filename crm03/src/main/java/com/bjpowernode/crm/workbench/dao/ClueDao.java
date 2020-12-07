package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {


    void saveClue(Clue clue);

    Clue getClueByIdForOwner(String id);

    Clue getClueById(String clueId);

    void deleteClueById(String clueId);
}
