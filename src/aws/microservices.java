package aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.runtime.Context;

public class microservices {
	static final Logger log = LoggerFactory.getLogger(microservices.class);

	public static Object handleRequest(Request request, Context context){
		AmazonDynamoDB adb = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper mapper = new DynamoDBMapper(adb);
		Student student = null;
		switch (request.getHttpMethod()) {
			
			case "GET" :
				student = mapper.load(Student.class,request.getId());
				if (student == null) {
					throw new ResourceNotFoundException("Resource Not Found "+request.getId());
				}
				return student;
			case "POST" :
				student = request.getStudent();
				mapper.save(student);
				return student;
			case "default" :
				break;
				
		}
		
		return null;
	}
}
