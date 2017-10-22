package tests.parser

import com.maxwittig.reportgenerator.parser.TimekeeperParser
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class TimekeeperParserTest {
    @Test
    fun getTasksTest() {
        val timekeeperString = "{" +
                "   \"type\": \"Complete\"," +
                "   \"saveProjectArray\": [" +
                "      {" +
                "         \"name\": \"AnotherProject\"," +
                "         \"taskList\": [" +
                "            \"Start\"" +
                "         ]," +
                "         \"frozen\": false" +
                "      }," +
                "      {" +
                "         \"name\": \"TestProject\"," +
                "         \"taskList\": [" +
                "            \"AnotherTestTask\"," +
                "            \"TestTask\"" +
                "         ]," +
                "         \"frozen\": false" +
                "      }" +
                "   ]," +
                "   \"saveObjectArray\": [" +
                "      {" +
                "         \"startTime\": \"17.01.2017 - 21:40:23\"," +
                "         \"endTime\": \"17.01.2017 - 21:40:30\"," +
                "         \"projectName\": \"TestProject\"," +
                "         \"taskName\": \"TestTask\"," +
                "         \"durationInSec\": 6," +
                "         \"UUID\": \"7ea295e4-8001-43db-97e0-7644bfd6c39c\"" +
                "      }," +
                "      {" +
                "         \"startTime\": \"17.01.2017 - 21:40:40\"," +
                "         \"endTime\": \"17.01.2017 - 21:40:44\"," +
                "         \"projectName\": \"TestProject\"," +
                "         \"taskName\": \"TestTask\"," +
                "         \"durationInSec\": 4," +
                "         \"UUID\": \"1a7d7c08-aae0-43c1-b0a9-5bc8bf2f3a50\"" +
                "      }," +
                "      {" +
                "         \"startTime\": \"17.01.2017 - 21:40:50\"," +
                "         \"endTime\": \"17.01.2017 - 21:40:54\"," +
                "         \"projectName\": \"TestProject\"," +
                "         \"taskName\": \"AnotherTestTask\"," +
                "         \"durationInSec\": 4," +
                "         \"UUID\": \"e9171668-a4e2-4982-ba94-b47a084d60ee\"" +
                "      }," +
                "      {" +
                "         \"startTime\": \"17.01.2017 - 21:41:03\"," +
                "         \"endTime\": \"17.01.2017 - 21:41:15\"," +
                "         \"projectName\": \"AnotherProject\"," +
                "         \"taskName\": \"Start\"," +
                "         \"durationInSec\": 11," +
                "         \"UUID\": \"adaaabce-400a-4253-b46d-2ede3272940a\"" +
                "      }" +
                "   ]," +
                "   \"settingsObject\": {" +
                "      \"theme\": \"bright\"" +
                "   }," +
                "   \"saveVersion\": \"1.4\"," +
                "   \"exportDate\": \"17.01.2017 - 21:41:17\"" +
                "}"

        val file = File.createTempFile("timekeeper", ".json")
        file.deleteOnExit()
        file.writeText(timekeeperString)
        val parser = TimekeeperParser(file)
        val tasks = parser.getTasks()
        assertEquals(tasks.size, 4)
        for (currentTask in tasks) {
            if (currentTask.taskName == "AnotherTestTask") {
                assertEquals(currentTask.projectName, "TestProject")
                assertEquals(currentTask.duration, 4)
            }
        }
    }
}
