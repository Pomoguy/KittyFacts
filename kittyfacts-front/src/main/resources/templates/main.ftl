<#import "parts/common.ftl" as com>

<@com.page>
 <div id="login">
        <h3 class="text-center text-white pt-5">Login form</h3>
        <div class="container">
            <div id="login-row" class="row justify-content-center align-items-center">
                <div id="login-column" class="col-md-6">
                    <div id="login-box" class="col-md-12">
                        <form id="login-form" class="form" action="/start" method="post">
                            <h3 class="text-center text-info">KittyCats. Кое что интересное о кошках </h3>

                            <div class="form-group">
                                <label for="email" class="text-info">Введите почту:</label><br>
                                <input type="text" name="email" id="email" class="form-control ${(error??)?string('is-invalid', '')}"
                                       value="<#if email??>${email}</#if>"/>
                                <#if error??>
                                    <div class="invalid-feedback">
                                        ${error}
                                    </div>
                                </#if>
                            </div>
                            <div class="form-group">

                                <input type="submit" name="submit" class="btn btn-info btn-md" value="submit">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@com.page>