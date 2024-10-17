package com.gagan.backend.service;

import com.gagan.backend.entity.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class SequenceGenerator {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String SEQUENCE_NAME = "org_sequence";

    public Long getNextSequence() {
        // Increment the sequence and return the new value
        Sequence sequence = mongoTemplate.findAndModify(
                new Query(where("id").is(SEQUENCE_NAME)), // Query to find the sequence by id
                new Update().inc("seq", 1), // Increment the seq field
                FindAndModifyOptions.options().returnNew(true).upsert(true), // Options to return the updated document and create it if it doesn't exist
                Sequence.class // The class of the document being modified
        );

        return sequence != null ? sequence.getSeq() : null; // Return the incremented sequence value or null if not found
    }
}
