<html>
  <h2>Name: ${getFirstName()}</h2>
  <h2>Surname: ${getLastName()}</h2>
  <h2>Age: ${getAge()}</h2>
  <h2>Position: ${getJob().getPosition()}</h2>
  <h2>Department: ${getJob().getDepartment()}</h2>
  <h2>Years In Position: ${getJob().getYearsInPosition()}</h2>
  <h2>Salary: ${getJob().getSalary()}</h2>
  <h2>Projects:</h2>
  <ul>
    <#list getProjects() as project>
    <li><h3>
      Code: ${project.getCode()}, Budget: ${project.getBudget()}, Keywords: <#list project.getKeywords() as keyword>${keyword}</#list>
    </h3></li>
    </#list>
  </ul>
</html>