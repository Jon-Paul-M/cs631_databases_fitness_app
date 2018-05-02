package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.testutils.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class T_010_CanDefineNewExercise extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminGetExerciseCreationForm() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/exercises/create")
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminCreatesExercise() throws Exception {
        String dne = "DOESNOTEXIST";
        Exercise exercise = exerciseRepository.findOneByName(dne);

        Assert.assertNull("No exercise with name", exercise);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/exercises/create")
                        .accept(MediaType.TEXT_HTML)
                        .param("exerciseName", dne)
                        .param("description", "test exercise")
                        .with(user(getAdminUser())))
                .andExpect(status().is3xxRedirection());
        exercise = exerciseRepository.findOneByName(dne);
        Assert.assertTrue("Build exists with a exercise", exercise.getName().equals(dne));
    }



    /*
    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminDeletesRoom() throws Exception {
        String buildingName = "BuildingThatDoesNotExist";
        List<Room> rooms = exerciseRepository.findByBuildingName(buildingName);

        Assert.assertEquals("No rooms with building name", 0, rooms.size());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/rooms/create")
                        .accept(MediaType.TEXT_HTML)
                        .param("buildingName", buildingName)
                        .param("roomNumber", "roomNumber")
                        .param("capacity", "20")
                        .with(user(getAdminUser())))
                .andExpect(status().is3xxRedirection());
        rooms = exerciseRepository.findByBuildingName(buildingName);
        Assert.assertEquals("Build exists with a room", 1, rooms.size());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/rooms/delete?id=" + rooms.get(0).getId())
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().is3xxRedirection());

        rooms = exerciseRepository.findByBuildingName(buildingName);
        Assert.assertEquals("No rooms with building name", 0, rooms.size());
    }
    */
}