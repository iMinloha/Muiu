<!--中文会出现显示错误-->
<html>
    <head>
        <title>管理界面</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
        <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
        <style>
            body {
                background: url("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_match%2F0%2F12015120340%2F0.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632225571&t=c5fd305f924f53c2fb9b30034a615dde") no-repeat fixed;
                background-size: 100%;
                background-size: cover;
                background-repeat: no-repeat;
                background-position: center;
            }
        </style>
    </head>
    <script>
        $(document).ready(function(){
        var btn_forbid_num = 0;
        var btn_ban_num = 0;
            $(".forbid").hide();
            $(".ban").hide();
            $(".btn-forbid").click(function(){
                if(btn_forbid_num%2==1){
                    $(".forbid").hide();
                }else{
                    $(".ban").hide();
                    if(btn_ban_num!=0){btn_ban_num=0;}
                    $(".forbid").show();
                }
                btn_forbid_num++;
            });
            $(".btn-ban").click(function(){
                if(btn_ban_num%2==1){
                    $(".ban").hide();
                }else{
                    $(".forbid").hide();
                    if(btn_forbid_num!=0){btn_forbid_num=0;}
                    $(".ban").show();
                }
                btn_ban_num++;
            });
        });
    </script>

    <body style="font-size: x-large;">
        <div class="container" style="border-radius: 10px;color: white;">
            <div style="margin-top: 2cm;"></div>
            <div style="background-color: rgba(00,00,00,0.5);border-radius: 10px;height: 80%;">
                <button class="btn-forbid col-xs-6 col-sm-6 col-md-6 col-lg-6 btn btn-success" style="height: 2cm;font-size: x-large;">禁言</button>
                <button class="btn-ban col-xs-6 col-sm-6 col-md-6 col-lg-6 btn btn-success" style="height: 2cm;font-size: x-large;">封禁</button>
                <div class="forbid">
                    <table class="table table-bordered" style="color: white;">
                        禁言
                    <thead>
                        <tr>
                            <th>玩家姓名</th>
                            <th>截止时间</th>
                        </tr>

                        <%ForbidSpeakList%>

                        <!--<%BukkitForbidSpeakList%>的输出内包含<tr><td>切记要单独成行-->
                    </thead>
                    </table>
                </div>
            <div class="ban">
                <table class="table table-bordered" style="color: white;">
                    封禁
                <thead>
                    <thead>
                    <tr>
                        <th>玩家UUID</th>
                        <th>玩家姓名</th>
                        <th>创建时间</th>
                        <th>创建用户</th>
                        <th>截止时间</th>
                        <th>原因</th>
                      </tr>
                    </thead>
                    <tbody>

                        <%BanList%>

                    </tbody>
                </thead>
            </table>
            </div>
        </div>
    </div>
</body>
</html>