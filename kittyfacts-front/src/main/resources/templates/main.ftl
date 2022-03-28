<#import "parts/common.ftl" as com>

<@com.page>
 <div>
        <table class="table">
            <thread>
            <tr>
                <th scope="col">ID</th>
            </tr>
            </thread>
            <tbody>
            <#list tasks as task>
                <tr>
                    <td>${task.id}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

</@com.page>