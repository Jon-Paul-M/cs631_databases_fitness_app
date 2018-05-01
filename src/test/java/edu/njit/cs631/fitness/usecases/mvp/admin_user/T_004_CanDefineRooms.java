package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.data.repository.RoomRepository;
import org.junit.Assert;
import org.junit.Test;

import edu.njit.cs631.fitness.testutils.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class T_004_CanDefineRooms extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminGetRoomCreationForm() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/rooms/create")
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminCreatesRoom() throws Exception {
        String buildingName = "BuildingThatDoesNotExist";
        List<Room> rooms = roomRepository.findByBuildingName(buildingName);

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
        rooms = roomRepository.findByBuildingName(buildingName);
        Assert.assertEquals("Build exists with a room", 1, rooms.size());
    }



    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminDeletesRoom() throws Exception {
        String buildingName = "BuildingThatDoesNotExist";
        List<Room> rooms = roomRepository.findByBuildingName(buildingName);

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
        rooms = roomRepository.findByBuildingName(buildingName);
        Assert.assertEquals("Build exists with a room", 1, rooms.size());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/rooms/delete?id=" + rooms.get(0).getId())
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().is3xxRedirection());

        rooms = roomRepository.findByBuildingName(buildingName);
        Assert.assertEquals("No rooms with building name", 0, rooms.size());
    }
}
