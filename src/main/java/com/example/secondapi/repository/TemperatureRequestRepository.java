package com.example.secondapi.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.example.secondapi.entity.TemperatureRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TemperatureRequestRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public TemperatureRequest save(TemperatureRequest temperatureRequest){
        dynamoDBMapper.save(temperatureRequest);
        return temperatureRequest;
    }
    public TemperatureRequest findById(String id){
        return dynamoDBMapper.load(TemperatureRequest.class, id);
    }

    public List<TemperatureRequest> findAll(){
        return dynamoDBMapper.scan(TemperatureRequest.class, new DynamoDBScanExpression());
    }

}
