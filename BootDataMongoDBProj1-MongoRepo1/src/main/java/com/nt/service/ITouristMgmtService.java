package com.nt.service;

import java.util.List;

import com.nt.Doucement.Tourist;

public interface ITouristMgmtService {
public String registerTourist(Tourist tourist);
public List<Tourist> fetchAllTourists();
public String saveTouristGroup(List<Tourist> tourist);
}
