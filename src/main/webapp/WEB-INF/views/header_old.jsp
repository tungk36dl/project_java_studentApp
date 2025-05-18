<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String pageActive = request.getParameter("pageActive");
%>
<c:set var="loggedInUser" value="${sessionScope.loggedInUser}" />
<c:set var="role" value="${sessionScope.role}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang Quản Lý</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container-fluid">
			<!-- <a class="navbar-brand" href="#">Quản Lý</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button> -->
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
                     <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
                     <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAU4AAACXCAMAAABNy0IIAAACRlBMVEX///81S4X/8RIMZYT/8ADDAJIAYoLCAJD/7wAAXn/w9vj2+vv/+gD33vBKhZ0LZ4a40NnR4ef/+/7I2uGevssveZTS1+P//wD55/QWNHfAxdYAKHL/9QA6XKL77ffMNaLZbbr///vnpNTquNvmqtPJGZ3PRanSU67jl87vw+P89PvXYLbxzOfgjMdmhsNdf7/Yb7nKLJ+PtMLdf8P///UtRYKSocD//eH//uwgOnqFl7s0SIA4VZYbNnjm6fDz4QD/8i7//NRtib83UpD++aKbqceBlsFJaatTc7P/9FFDY6aAjK3+94f++rP//dr+8z3pywAAAGNea5b+9WcAIG7+/MfnxwA6PniyvdP/LSjkux5elKn+95P+9XNHV4pqd6B5pbbYoJvgszksaKI9NY4AuFI5QokAr1b//7HnzN3s1Nf//3Pw03LjuYr57tr45UP15rPqy8Dpxmzz4Ff343Pow6Ly3Lzfsl/nw4Px3Nfu0n7szE7x2az36Wjw3MnhsVffsnUAEGf15pIAAFFze5rgubXapSfaqZLaqIDQj1/qy8fkuS/esLfWmkXgtcbWmnzYoTHdsZjSlFnRko3SjjeFQmyePV8kf7izPFkleHMAyP8SqOUWl2VqRnYdh20rbHnpMTTRj34OpV7YNkQTnt0AwkvpMDCmPl0dQJWENmDPHU16W4yStdicPABtrqLOgDjUnK/IgIzOixGoOj2jHB+fEEa+XiK/XEvHdni4Y4bWmlu4Uiy9aFu8bAChJzugACPKe1idAAAe23TEAAAgAElEQVR4nO2di2MT55XoR8PIlqURNgPo5Yls2ZYfsg0YS7JkY8kGCxCyLSMjoxpbAtoQ7CQQ8oQkDSUXDAnGENht09DtNtncze7eu7fAptk0Tdruf3bP+eb1zWgky0kIgXDaGGk0D81P5zuv78wMwzyVp/JUnspT+f4k8Ki/wBMlC/lHctjY5BP5M05wj+i0ZicfzXEfqgyxY4/oyIu2oUd05IcoeY59NAcOpMWFR3PkhylTIjf+SA48xPEvCI/kyA9PAsy4yE08kkNPcO4XA0+WNwosxs6I3KNxCbMc+/74kzXcJzgmdFZ8JMYzNibOh8YfkaF5OBLOAslXRVtsQ1s5Ei6Xr5CKDmuSShV8rkTCsYG95G38i8w4t7ixb/yjlmmWizEvibaa43h7wgUco8PRCpICqDUyneTYc8xpLvttv/uPUIZEboo5w3I1WTBHgmgkQBse3r9/7969O1B2kr/wbv9+Ahk0teCrBSkrzvmZNDf2HU/hxyThMS7NCGkuva6DtbsKRCcR5E6UHQaRFgJUgjRacK2zw2lOfBlGh/jK93Qqj14CMeEV3jYtvMqPhauu6HAViOoRkhK8gwcOHDp06BlJDh06cODgwZ0yVWBKkFYnOoFjfZETX2M2Zrh/vDI5wbwugi94m+emq6yWKOAIH967g6AEkM+oskcS9f2hAwQqrEiIRguJintNi2f9YZs442ey1Q7++Eh4bIIRIEqKCWhBK4jDlUKW+wnLnQcPyRT37bMYZZ/ClSBViKZcFczoGP8GM2ETzzNM9mGFvQ6HfSOhxneUMDfLMOd57rSQ5F+qsI4PDSaOcQAkaSWAHAEpo2mxkIX7CNJDEtG9wwSo6Z5fFV9nxsTkBWaITZd/am/aKksVr7bNXvnk7E3Hj20GOXa8qWyEkP0aFybgiE3lO9zaVPkYegmLtnHGn+SygfTcBdM1fDjK92ss98jMqgmsICE9SEb9fhzzZkD9by2M2/iXweSIJjibGlTZvrsCtUTDtkqn1nQMNqxDwT1sbtL9JI7tsKzRuNPduOaWsj1t3lyrggfG0KO/KXL5l5bNPicwiWIelFiui1JDSogeOkhUFN2SCdDQS2kOldMmvmDCo26TInUNjebYdjdsNz+xrZsbGjZRUtewnVZGx+a6TXXbjTi3wSaNZTjtdXVlemwqgTzzJgx05oLImWgHwxCbSWCiYu4pt5XrEJV09KCioalypzTE8W+FMHN/hymrexKcRL3qEMduky8IVBrMTtWxu1H5LeqUF7odbAQn6OxxMzplkp+FgS5ChPSWyJVXce0FZZgfkBRzgzSJilp0QAvGE1jk+HOYar4VCpf9oIizcTfIse2oaGbDGlYxO1XH8QYZZV0jCIx5fPMtcdq315WvaSqTYzHwQ7ZJiJVsZUmRaziaUmHuq3mQl6uoCnQYEir9iCclkHAWB/xUWdGA4CRWy4546spMHVg1XKV88bEGeXzv3rolkdiy9fh2APptcTY1mP+U5ZLm8ox/jrcNhV4RZ/Uf2aVxLtnMbw9TD3SvccSPYwi/wPEvop4aQ88mitXuBsNoJbKlzlRrJZoNmykz0ARAvyVO/MnqKlhovSyI2QBzjoVg6W3+l7pPXFFpnO9EmN8BJQ10pzzifdphzogzniFWnAkBWM6YldE4GXL6RgeLkDfVbbabLW00wN/9LXFubSS/TS3O6HWsGgsv87b8UPIc/UFBVs0D39JmmgM9gMUSVFAVS+hGGvyQeI6BAV9WVtLhxDfGGMbRuMnkVLdINMtixSZai2vHKZnhuuMVENISmsG63IWkmI6doRbDQE8NK6r5fcAkQPcpCgpmRB3w/ncg8HwLh7r7fNn50zjBIZTh3CY5nIZjuqXHiAUwibxp3a4ZJx6XHGR9ZzTBnEuKMMZe5G10gpkAH6So5vcF0xKJWEZkBd2biu7XDGgWs6IJGz/nHzcEFzqcONoNOB3EEW0yENhaKQoo27YmnGSRMcwylQAXY14UwXCG5sQxyq3CSN9RWTUj5YuK9Mfqm0iOWjtSykVUBd1BOfjTmLJDGJ9cZhYMRYP1cOKortuGWkufKipn3eb1zr1mnLj74zDg69bNjGLcBBO66Ia/50SI5VVBJ0QcuoQqThOMF4txSzwXB0Aj+BfEG402A8U4rgv/8yHP4kgkUopGpDWJ5IbJK+Lid+5X3VE4Lc6FAhApfcAwrGGOYz2cx4l3AsdT16gtTJSpq6nUihN1vWHrlsYanFEYA3j/DA9Z+0U6TgLl3HFwDxno3pQ9kWnOuLw536jFcTiVySXgvS/hi1uKCXthxNfsK2XsiVLcZS94R1x2eymRSERGfYlEqbng80Z89kLcWyjlXJcysAMQHPDo31PysWIie45ZsLl/JTBT4qv676fDCWdkCDwTkl6ijlKWEonU6Y2pmdSKE2IuDJJw7fV2KpzFfP39pMgyF1jNEYDp3Al2k5hNQDHqOOxLZIoub9FxqZBxReKuku9wtNjsyxx25VxHXDl7ZCSRSl0qRCyXAHviSNwy6hrJ+I4A16jvcMri9ZVKrsMAmCj5CKonxJ/ywQJjL0AA7z4ZwnTTUNLS4dxdZ3Q5GA81JiS11T4hY339qKZGnIk6yWqaZ58GOe+2wfh6EavH74bUpT4c67LdbHYlAGQi4xpxeS12lz2TaG52pQper6U5kbvkK7kuuUou72iikPE2gyVwWYp2ly/udbkSmZyrEC1EYU0v6KqvOQFbyeoJ7igaVSzRS1MxiDudDESfSUNJi8aJAbveWztAOUn4QrIWxbWhI17fzCk4jeuVUSOWxC7HFes4oynPHMadoTd43SxmQcIp2cVC9HKilPAh1CLzv1IZFwzcHMAtNhei0YQFlhcTpcuujK+Y8IJWWnKJkSL8C4tSCZfrMqyJ2plzRWEfcRnnoR07o1FNMdI43mNpSI2m9a5dw+loaoTT0fsXQnErYzhVYuWOr0tTjgp262UbqjaNk+ya7A2dURl9nYTT40tJEUIkzxxPZ5gFrCHJOEejiUSuUDqcGi7ELYlL0UyRWMVECtCA0uVSoHdgLotxnz0DLspnKbrQdhZgLZ/lki9XgMXxQi5XKFiOFHJkl/sIzmE1UhoXxReZANC84U/r56YJzq1btmyF00Sd09ejjqnqRU5V/nm2lmlxFZybGgxSZ3BjTarlIFFE1f2GxxaZK0mM499P0tNumA8dxCoxhIqWeHNzZLToOhKBkeq1xCPxTKrZ4vWionm9kaIXBnMEVpEXjVoi3mYY9V7cChZGyOJ4JBKPRyyjso9/5pkdO6lA/l00nEBzxTPElePcpJ7mdr3pSmiFCdoQqDpbE04zoXFu1izHus5IwAII8IS/b/6S6mJDx46eKJK7XIzIoWYxIqlWsQiMI7rQMxIv5tQFkSKESfLKOToctZTiykqSa1cDz3eSfmbWxp/0MLOivrmmSX/GDcdo9cTcTzGsqKibKZw1VNNqwklUfRu148qTiCBv8tw4c0UU2fEwpZwOdOyHwBNFHGAy44irORU9nMDwc7QAujlaAqcCUCEgjWNk6ctFmzEYBcy4pjfnanbBe2+qFLeM4FJMiOKFTNQr/Q4GnMxLzCShOcmxZ3Tfz4BzUwOloPZGqtLZpHmQjeFsNIoeJ/5Myk/maFzPGV1IcmyeucqKuhl2O+JExx5JgM/xuTKZRCHqK8GbRMSbirpchYI95wLXU/C5CiWXL5PIgLuC9/CvKxWVcEYSrijY26Iv48pEE1GfL+4ruHwRF+qxESdkmO75ZWT6sv77UdV4UpAHC6mqB/HByqB2qB6jBiXScNZt35LQy26dZ7fr3Npxs8hKJ9fcLFjOa6KumJMYHiaOPRKxp1wQMRZcR4rACYLIRBFw2sFtp3wFcDVHE4ftIyl70VdKJQ67ouDwLQUXwXnJhT8FhFGFaALcVjSXiDbDzwK/RdxEO/OQry+BbvIzhtkqyRVJslsqyCvWi9DQPD3B4FC2qdV2rhd34jstEdtSobCiyNAC854Inki4qeuVpXBehvgmWnBdLqRSh0HxXDmIjhxHIYwvRFMliJAuJQrDECplALsLQigX/D+agvj0csKL0VUmyjQnSlFftJRJRF3DBS8TRZx7DDiB4xJaz7mlQKVAiSCj3a7BgdtVv0QCpRpK57Xg3K77ySQLXTlWGmJjoWss8AxdE6lsGXGSOClS8EWbU6mMBXS0NBot+qK5eAkScVhg8UWLGUt0NJrzRSOFzOURV8aSKWZKhUypBM7flYnD55lcMToKljVTsvhSXtjem0DXNmLQzrw4s4aeHTR0UV8D0eNkttVpdZ1jDXoYqjMiYfz6OWYtOJsMsRG+31Q5M4qJWUa4xrLclP8tqgdD0U4Ie0bBwUCU40VnEo+MYt0jEofg5wgEQqPNoyRwgpQSPh1F10TiIQyucDsMj9A9jeLGuCDSnPLFTVxRjH0/ALoJ1jNvEihROClSiUbMiOyqOLTisimmb4VT+sk0SWw3n+eTJXQW6x5XRZGbilGjzK7iLBdAGy9eLriIFEoAWv5g1CtJ3HQzZWsp8pQGu+YupsJIcwlsqPh6NZxUZrKbTN/QsknxGrsbaorj18cplaboYzRuqvpLvcbb0mHmqk3fKWtXBnuZjDYXMz7d7sCakk+8MmFXqipPOu7UcAayNvd1pMnPhOidl+HcpoaajTqfr/h98tkWQnrdpH19nPKkhv4Y1eyycNLNZWPMVciM6ElEKVAqUy3viFlfjI/UNZpdurfr4xymcnbQzetOLCPrp6uq4Gxq2GQm5FQdpKS0rjNaF6cyqWGQylOaAcY5z3NsjPkgqZsVNsMZ8eZ85mruat44TiyBqPqTF/mTTmbBxorXDLs2w7kJ3+PEL9Cgz5L8IX63qcKUvF7WxWnMIdTfrJIzGppklubdHDvEnLCJ1NxGCpJMPc74aI5STLvLVwDxycM1NbpxnDvUeifDTLqB5qJNZG8KQ1VdkVotIzn68S1bdULKxhhwYky/qWG9Gt26OElgu9Ug1cpV4bEhZgnGO2RGV1h6qii6dwc9uR45UlJhulIlrG80N3vhr4xkZGM4saK0Q63GM8zidUIzeRWGfVWcauh+XK4b6/mofr9JCvjLedLw1sNJp+ua0IUCowiviGHGf1vEGt1LVF884jyk4ox4S/I3T/hy8eZRqv7RLDGB1HwDOEcQ595oQT3clBBI29xzq5BsiuU5O/Xlm+RGkAqh5W71VI9JPA2nbd+8kbaF42iBy8BVTRLeFrPTjHCTNfQd+6QCnTSJGZGHud1HhUUKaQv5KLMxnFiN3xulOkHCaQ5zdqD5SjXPjmEfOddt5qHQFjUzkrxI3XbdOls3b6SDDtN1s2xgc5XMKDTPs1OMcDWpn2Z3RfdL9U4ULzliIlo0Cym9ZP3oRnEe3Lmf7vzKciJYz9gsZ3tH//30U2+bG+ScnZySyYgjgCR0ciB1TOkkdmw9DoHOBnCqlthkhYolgaUZ3jYbYFaTPG07dYGnN4ElO69RMSXtjJP1N6ydB3cOa8V4ZlHkb3iYKZbD2cxynFLRZ+txJFSHp7p1U4UeAupUmxokV9+w/dju3buPH9uOrbMbwGk+k8RI6VglZxRm1ubcHGR2S/M83Y4YpSIlAJU6UiE2j2fI6rnIhlwRhp1UnDQELj2EWSZ703g/Eq0a30A6ievIED9m3oOoH6BbGpUGT1LMlyLw2nFW7kE8XnlKcyHGLN9gRdtkYOkkHXjSkVJ8uFiJUCROVMzl1Xl2SEPjETNd1nBC2Kk59mn2RCg/xvEzJyBmqlo+biBdXJWsmnyqmxS/eazOEDg21NXe8kXmoUyrpltMGyOJ5NMM4786A6nRkJ+e4vZJrl3yRfFKaOIjEsPLERpnc64QLeWK8dFKm0mOXfNE+ZcghBfFi+/Dy7P670clP5jjHSNKsbuy9dpKlZwYx7bNDXRv/aZjuo2q4txSeUg7NAttlDC5b8Taimi4N0BieL/m2itoZnM8I/16viMWGucRYgAciUKp2RQoms4dOk8US9v45Md+0FP+Xf33a2pQsuXGxu3HpRHmaGyoa2hkTMUBABs0RPamzY3yBGXj5uPGXkb9urJswyNuATWvM2+5l1cxL7EIb/Gz4IL8HydJ+4IqdjCeWhEkLheLIhRLb8nnUPhZ9DhLyl5cplaCVDuHh7XzyHMif2uNwftnGNsW7FtUUdd34LtKkxdkA9qB2Lc2oWwxMbVktxV2YPoZ/QXMI/nlJM8tBFBBed0NTlKULyraHUSkSjrhm9NicMZV0ufsRy5rn0XNeJIUUzOdQ5x75iao5niaE1+scAaPjVwBnlnI7DzXkiK12EflRV7FzKlO21BXSulcUXOJumbPN1pG05gTLYonIR+KLXKi+JU+iH/8JMxcmXeL3OwQI1yZoea4sSCvGE85GoKFsnaOGoeaPu6MRHIpdSz4mk1MJwTxVLPsNQ+Mc/Ds7Hv+h3OSP5ycmWCW3ktC6Akj/gJdfkhRxnNEPndBbk7wEtVKuDQlLUZ0cWfE26wO5oKBJzGd0WF10+m3YZxnbbx4fZWZNniix0+wDL92g+W5Mf2kV8FstEeloRvJ+YZJWamoMTSG8d6iMuJzev++j5hOyvZCcmkT3fMnPExgTB91PobypggYQ1euQ6iUplsX6FApkpMXumRNi4xKcXrcIg9q+0hZVhTPyTwTOneE9Q9DywIr8jPvLWO8lHzsb091ISkuDoEnujnDi2N0hjcc1aftKA5jrulVtCxzqSzJVMe7Xj2lMElzVpNgNG+sCkx4aox/7SGf7A8gH7AiNwmx59LtpJu+QJ9OjEYVMhlDYB5XSurRw+U5u2Jy9Vm8YaznRX5+NST5oluPvXKCnIAc04YaunZLpIa7XFWSa57yQilkN9HOlAlOr/wjJKjfoKw4l56740eYNlJVevxlPLx2EjwRtxBjQufpdkC6DNKsOCP9aPcqRtVUO+NKekSN9hFDNSnwihM0NAt50dxV/5Nw38nxWcZzAoGOAVDaGWEN+YDijBQyamJkiURGRy+r0ePlI+U4I0V5UUb7EYwxfEBg8mmbKM68t8TkH/swicGLN9IBADov8hyrj5UMRU8iLjlU8jZbctT9ZxJxs3pnRF5hWE2N8KINXQzPDAFM98x7awIzxT72YRLK6yy3EAagK6zbprtZDDojRT31AzdSMtwcKTVqhjMuY4tqmaah1IltNJCxL0E0P8ub3LziMRThmiiyi9MA9BbL63pr6LJSXMaFtLzGXhCX+eRGxIizrDYXA5uJMPOzNvd8pRRzw/6+QuZf437Mt679S4RuJnmRnc0D0Ou620fQdRBFPe3FiKaqsrhGKkxuyEG+Zjul9g9tywmwmaHwFLgi8aQZTcED3t7ZRn1XT4jxKBJi/PIr2BZf+qWTbuvTNvB4FBB97fIe1a09SigRau/u7nbKb3ZpAYagrsG0edRl61QW8sLqLQDKpU8znvO0dy2bgUNJGdXTHvVGTNsWylxR2Yxw+NUlJrYwBn595ppn2uSObc5gp59xDjjVBYPBbmYgOEAEXu6SXgYHGaYXXra09pBTb9GAdAUVtoPdZI/q1j3+YFBi5Oy0ggT7Jb3sHVS39gc7FV1t6VG/UldVmsyZNONffW/GzdvYBf0pSYm77NzlMqY9QoFiEoXSaKWWr/JA6Zk9OyAjoo8wvchxvDh/dYmZMvPrTivg9AxoJzBY380ErZLU9zO75Fe7gIL0Es+6rV7VZ6HTKuMMdbZINOStre0Kzj6yC/ivi7BrtarKreF0BhXITus6OJmzWYg3lz6+zrpxEo76QKeeRyj1xDg04fKVLGqJvlnx1hrOUTkeUsJ4crWbrl1hHAPO5EXMihZYs7szEu30DFi7VZzwsn+wv9XaMjg42AM4e/vhxWAH4uzt7wpaB2BfbVaros8azj5poX9wsL/L2glbOWWcfUFrcLCnr73Lau2VcLYoKqnh7LCqYNfF+Q4rzk6hc78BY153CbROPRVlQ17FTDHiHaUm5SKljCSliHFRSbucaIeuD3EKFHPuxaUQE5tI82V3WqBxBhXLNSiR7bF2EZO4y6qMQcDZwQjtA9Z2grNXMOLstqo/Srd1lwQLdyt0WSV7EOq3wi4Qp1XRRA2nslItOBnsj89OjjOe1dszotG5l6VGqJ6RsunNuCzU8oi8qIJyBnj2+gdOJpBfhHx93tShKjitrUIFnO00TiQ1iDiD1jYDTqCm7kSHs90aVH6SQWuLn+BUlFvFGQIT0V8zTvDtbpbjshMxpu/qWdp++oYx9pQy94hcwhSqTqJTNCVR30t1Y005Jy+uepjxSdYGI97Ur1M4CasacHrqu4h2Wgc8epzOAeuAouM6nJ3SG2kB7gNxdgp6nMDc2lIzToZZ+4ekG5y7LTsR1jViOCT1lMvIcvxt0uhhcufJeAkkl1MuJJSvYqeU8wwztDCGLCFfrxAqyjhbeq2yd18XZ7AFcfa2yCet4myztvYrKkvjdFqDWtzQjQrcau0dkFVRxQkesEv5XWrAGWBC7988mRSBKDuruyzSRcrISiwv+5uSNH5Hjqrs/vHX6stTp6R15Rkmh0xzH4k5qUInhJsssGSTFz+oGMkpOD1Ba6s5Tp3tlE+2rb6/XdZnFWevtadD3ocOZ0d9rxah91lbPExrfUe3bCkVnDDWPfKRa8I5BNml4F+9JhHVdTBgYemQIdWUCuxHf/4b+QZL+/7pZz/7UNbPU7/dRHgqrj7VrAx1fWUO54F5cebkB8vwjfMVbhsv4xwItcl4ynB296F4FJwdED0BzkH4JOincHqCA06PYgFonBh5qRIaAIyt1m5hQPJlCk44HIz3rppxMq9Kt8r1rN6E1F132a59/7A23I/IYxXnz0d+/tFHd48acf7id88++9tTeBm8vLmsyHsMs+vhMZ5P3vpgmVBZyFa4h7WC0w+0giEznEEUXNiL497ZQjw74AR93kXh7AAI8Lq9DGdvfQd1vC5YA3DC/slxFJyDMPg9wXpPzTj9M3x2IY8pkXPt6lu6GNAXpYb7qOxJ8OK1yN27dz+VeALOXxOc//b7Z597rgEv1JJHdWZU80N0E+Ls2MsQu4PE8gsia2jrNMEJrmSXGU4loMe4s7tVchiIk2mvR3gKTmQEGHvLcHZp5gKkFxIAsuog8e4yTj/uRNglga8JJ/P+DM9xbHZyCrRUeEeXHJkO92a8C9rdux/95iiN89Q/P/vJc8VTWpne5VX80AFdnTM8JQhMYHpqYQzsJ1txjkjDCfqFeMpwDrah9ClZEbF5BCcsgLEt4wwRh9MXHAiV46QSfA0nEOxScfZZgwJxZkLNOJnlWywvioA0vTgxpMOZUK7HJt5dRkKG+z7gSRYf/def/fs+DKWe++TZhlMadYd0dwEy1HV3/2DCQ6cX0xCciaI480HFL0XhFHrRT1RzRQPgrwgbCSfRZxlnTz0mkKFOaXUdzvp26nhogAlOHO4d4IIIzkGyP9n01oYTk6JbMyxELRAucVld8i61yivDXR6weLepoz//6C4xn4DzH/eh4XyOGE61XkIPdZ0fWsxyHIZIIjv3q+XKz+yjcMJ5QIheLVBq2yU7Xwknhp99jIRT3qxfCoBonK31VL2KaYGQXsIJux5wyjjlX6GTkK8RZ3qaEZZWT9xYmWPdbl5/s8IUuZmFHMyPSMuwFGI5+puP7pLh/oef/RN81PDsc7/7BXp1ZahfUr06DHXND01yHO92izPzb3wAkTwzWXGSiMaJaWJffxWc7X31UqAu4wR9HgiRwRxqsQ60tLR0tliDRpzd1kHqcBjeyjghONolEJx9wWALimS9axzsZ1jpLjGepdWP/349qbvtuEMa7rL5lEtLLpzVPHqXqOc+gvMXnzz3yS/K4ik0nOjVtUoS+J7k9dtX31/Cnz48UfH26gacYOc6d1UN47skNjJOrB4NEpx9VlX6DDj76lu0+iYpdMg4ITKydg8gzm51Y1y1RpzMa0lw7uNDsTCWYv0X3qSjJZd0DbHEU+lHIN1HI3dRPff9y8+OWiKbnn0ODWdkRLKRjlxcM5zUXbmHfvnast9PUA6NL46JhhtWVMaJtZ+qOHuk5EnBicl7EJ1Tv7Wli4hUm6Jx+tX0noQB/YyKE2Mzop0t1k5p6yAO+lpxCidmeHRF2fTi5MLpfExn0ApStCS7Izn6vByXhjvBOWI59btnf4+GU5kHyXiVdAgCeNqrBwKx/OmFhcU0pEV88ldVuhD1OIFDdZxw5oM0TuCDvj7UVd8hhECEjnrMxnU5+y6rqp4dRHlVnODLUFs9aETJ1oO491pxMszSHzFxB6SczWbjxhZpd5SK7t1x0FiZx3siHr370c9HACe49WefQ5pHKN+vGE46gJ+aHQNnBwdAX+Sev1LtGxlwgmuujrOdlPJUnDjKASf8lX8yTxADJh1OsAgynz4pk1VxIl7AqaWhoPxC7ThjMWbt4+tzM0nINN3gjnieutTIMZzCu/w9I6WVzfJwBp4j+z769Oi+D/9136nf/28c6kdkcKkjCs0DO6hCUmAW/DkRMKAzF0+Eqj4JxoATvbsBZxtJMp0yTmm4ajiJ+4I/KoFeKZyncOIqLX2ghGgiPTqc8BJw9qq+398Co91p7cQjrt+KGjh7OsCEltbWVk9cvfbVV7dOnmUp/UxIN1eS9DMiT1ImLBHL0U8/2hdBnMQPeeXKR0FN1YnhVNKhQJpNzp28+Mavzr/2wZU1T4iZnt0ITqa/3jTJ3KXm7JiLUjiJZ++0qolkGybjepwCpFbBzt4Wq1VSdQonHD4EHl4tObVC+gW/KB6RTk0rSCgJvigcJk9ZDHmcHv8Z+lRldyTzLEqA7KOREctHv4l8+AdIiCC5HJWduo9y6tStT5np6TPLypRjIBAen63eq+DEgq4zGFRxCgMSznqpItkqu1zA00VywFALnHFbvVrCBPB9dA2OvDi6PsUAAA1iSURBVO6vb8XXHnmuKDQo7WSgQ2amFUXaWoS2enX6DRLXAcSJaW0NOCHTdNs4diybnp1cmMjnx6f17mh4v8YzXpSGb2I0cvTTu5HP/vBvvwPlbJZpFjSaO3VuCB82NT0+NTUxuTibHWNFm+H2Cgbxd7cJTKi7W/NW7SQQd/a3CdK7biKgVh39JCPqgU/6+qlMZ7AHVlbfCW39TliHfK7tt711oKWz36kcgcriu0I91NtQd7/gJ4fs14qkVWTtFuvmIfWTvBG4i1k6wtbxlK94YxIjEctHe379h1OgnEfkMlLhiEJzDz67RHVDgYW0umcQnp+r6ojMRNhom51QU8O9UKEtwf/dLn4Ird6eSyZZxV3wvK5vSb6vp+SPIvJse8LSfPfTX//Lbz85pfp0zW4SmkrJeAjbE5Qdi8nk9ZtPQgtiFRmaCHg8q3fu3Lz21Y2LJ09en59LstQdplWeJP6MxCWTaC/uu/vv//G7f5an5hwZr46m6tTzIgvOfGZ+/uRbF1/+1bUTy/6KZeMnRd4dm5C9ueD3OJeWlpbfmaLHewp5HlLjeWlwO3Kf/p//+GSfrKzFCjSHXn3pnQvLS0tOp9L8Mv78q09Cv3E1ecHNZWdnFxcXJydnn4c/kCDpniui8JTyTa/kkAL/99B//j+JjOtIRMmFnsEHllAV4/H81OnTp6dATk+ATI7xZ590mkxoxS1yBknT8RIZ7wdUAzoqjXDf++Qfe6ZZncs4pKMZmFT2ZlNeuOce94vcahDP7RkWHAUl8IY2cQWMPw+qDzxQ77uCH1mkogcM9D2QC+2PqnfyiqVFvfB88qTh7pJPpoSWPr51fV4nM2N6ntEd5AES0s1C4nE5EHLJV1yTgX6Q0FR8+nQ2CU5oBvJXSWaS829c+QnoJkgYhjZ4IacmHiGkm+3wkSdyHNqjZPDekQIFE30QPlZjmCp7jF/wLKMsLcuy5GfCJg2IT6IsLKq2ErKiQDgWhv9i+kvilGdyKCURS1GBuUca6HtTw2ouFBgiuwgHmICaZg0tnvxpaCcjnOXBuafT6Ww2O5YdUyRNDXh7NIpPMzqkPk8vosEkqrlfe+JLPq3uAnaXzcKOZ2ez5L5JPw3x4ySxKqwsnK47BAzo3p3qkzMtFEywmjuGNSe0YKP2wGpe/f2Nfy/hO+nzowvKluZ5kVWFOGI3VkCfp9ZxDZMnwUmPdcUH6+2TniwMXmq/NtDDz0tbEm+u7dE9v1bTF/G3K73DPe0C4+zXPulr9+AfJ9PT7qeWCT3tPT097e34KfzTp2axQkdrq9S11NMDu21vJ2u19ykHgLVrqmp8K3HemZ8RAYMookeem5ubv3Xjva/eoO/oyzhSw8oTt5QHYUtPyopSsfviC2989ccHK/Pzc3MzSl1aTM5/uVTb92ivl+qaWBbzME6q2Y3M0mJlN6jWykLB+r7QgNwX0s4IpI1B7mkPdeE7LKt7BoI4iS7X5PrrpU4Gpq9e17/w/Up44kz7iavXrp44sXqvfa1vyY9TJYKx7ILPKlQe/Kg8/HbvcHSYrscJOEnjdy6trd0DufPxxzc/vnPPma/Rq7crbcTOgQGPrsuftBu0Asp+uVNGatbwd2o4B7S+EGySCUotr54WGSe8rQ/2BeXpkV1qM+3DkPGx56diRIamx/Pj05ggTpyG/6am6ZurgQUd3kse5Sw97Bphak/BjGkSJgKOXRDC4al0xQeXG0TDGSQ4tVnHTgVnj1XWL6a/fhc2w7ThIIbBLgwE2zpag9ZOBjs4rL1O5y5cFXHiYG+1DsLfkIzRM2BtNzn+9ybniMk0ppuYILK0R0qojyCG//SPFJ1eHJNdGfoi8Oss+PX0bHpMFE374M2kDKfaJK/i9LTIjTUCTm8ATvUCoAG0DR1BHMTt9dim7e/skLQTpVuan+iRSvUd6o/ykORE0s2aiz6HTxSkR7fvx3+oyzQpj65tiSKKX9U8rMpwqtcQqDil2TSyitWJONWLNghOZhBnzvrriRdDp6XgVHqRW9ACgGml3NxDkXu3kjibqdR7iYPmWRZzRFE3WO2FKKAExfRprcVDWXkDuVaseHXY28yXtX8Ho+0cUJvkNZwdZDjjugOogPUGnO1IUsaJYsTZjbOVPdT82vcn4aH86YXFhak8uQLNc/+9lfk58Ovz8ysrt27cuP3ezY8/vn///r3Vc+f1rsThKqQKCUrnwq++fP6r27dv3LgFTh29Ov4ILFaPV96TQ6Sh8amJycmJ/Hi1mUyjdna1Kk0GGk7QShIqtaKClWtnu9SP3KWMZSNOJ1rNwZp7EWqWwPhkWjWS2UkEFlpSxenxhDZoXUIhj0fZfG119cSdOydWl5bkyvGsGtGPzU5UJGrUzk7/gNzgruEMSZ1tArGhZbaT6cCJSWe9tV/+tY048dICCK9qmZncgAwtYG8GZeXI7VHDJCeUZJaI+mbytPFx1YqE8wuL8orZRZDnKclKd2ab4ijDCsedreDqjdrZKXfN0jjlNkNymQDx7B0ofgWntE6v1dopxZVlOMFIdJMLir43CeSzkKm4eZ3bGENcFy7OJZNG/67qlRmE2GSl1WGoz18l6yzovBTE9nBk067EMu3E61TIrDuFs4dc4NdWj/GnGnc6JZyhbisxBWT5oGCGEzt1vl9HFB6KXTh37Y/zSTq35EiRM3zlm/kKHh5UeLYsHM+z5Q5dppZc+bvUA79goxbzYvLB7RffuRCrjjOo4MSmYkGHU+rT6CJ4FJxWgrO1tVO5ItCDvQkYYpbhxK6a4PefEQmee//1gKU0lBvLw7Dk9UprAGp8BPg4V4km0UJyp6ZJehV+7i+fLVUcaSbaiU3F7TqcJNcU5CtYO60dPSghJSvqlS2+0N6iZkUoGk7wZb0PJegU7n/O0vqJj7t5UyzDKaoxkGhb0O0gNsaRxVLZw7gdn0R7vEjTdK/8qVppyUQ7SdesX4cTr7/qkS6fhkCJckUt1mCHFm8AasgCynFC/E83dH+fsvxXkaZggwzoyjyrosESBpucf/DFf39558q5199eSHNSFVQurS+Ks+++/c6V1ftf/+WLlbkk71YiTjI5dB5OOJbV6eb8n6t+HVOcHhzANE4neKFuqc1LH3f2deo8NskkTXC21tRx9K3E/4XeIS2GmdCJr+Zn5LBx7sFfvrx/bzmk/ubE5OXTkEUuhJmw2tQkCKG1+1/+9zfahiev4Z1j82P6kV6dpjlO0mXYS+FkgtaernqSahriznZUZE2wFfyHxcmsPSh38KHle/e//Prrr7/88/3V5bIMMTxJEnMuXRY5hZbk7e7fI/YxMKE3rcmv10k3e5Sr8vuCpJVOwil0Wbu6aJyD1l1BclGcESfYTwItpFw9OPhD42Tuz+t5qkX4Cm1RqsLpy/WaqJsNzer9lPuL9SY4+pTLKNuRq4JTapKncPZYlWTemBW1kVsnAELyKeaaPzRO4bOk3omYx5eyACIq+M9W6TmSlJim+c26JXm/UjdrxRZOFSdpkqdwYhe7BMeYs/tb8AOnfL0r1px+aJxM6K+sgactO2Ve7w0bRi/HLVZIGKcXjCGUe+Xe+t+lVUqmPaTdV8OJ906gcGLSI1EsSzK760Ex5YJRHxbrfnCcjP9z1hAcQRK/mI8ZkQamsmVRJsdOlJGPDU2ky6J798o6bogIDONdHj946E4PjROvHqBxtknXXxGc/eQqTTkrwnJoB4Ha5nd2Yu3ph8fJLP9X0hhsctLVmvn8+DhW18enyRXpCiNyOaA64qcwbR2SK/lTC7NZrjy4dz+4X8s3EVohZVFuXkHhZAZpVwSjWW7fVrOidiVn764H6+kPWvGSTfwJHgFOCD/nTYrHar8Wzy4I44tqxcTNz62sQHSq2YY8M35WruXbzPIknv+mJppo7/AOR1K7uhMvRJElNCBPvRHxt8hTE/6BemrqDVNHD9m4R7lOG+0GWVO7L4DQW/+wwnj12372DesuS2skRRTPvi0MpRWYojv5zd8+/Oyzz/72jVq/J9WR0LtJ3jw/Fd0z/7Na81fp2NU7KN9tpo+q8fagMVUnfgflTwRnnyR4LWUfiZ2cZCtnv7wXoU+uLGnXsjhruKzlu8q9z1eSZUCBZfKFC4G85s7d7IO/3V/GGOrC/b+usBTQhSHh9VfwZhjGXfAz33y4oRYQ0wBN38NQQy39EfeSLt///MEMZJVaC6HIzl183R+Y0Gwmz698fl9Fs/znv6xoTowTZ08LF94+STrtqX1c/+Kvq098m6yZLN/78PMvHmCeCEni/IO/33nfyeQXRc1miiv/c1+nZ8t//nxFVHWaA6ITMeeVO7dX5rFpAXbyzX/+7bOfJkwiwvK9e/dxfujE+Xx+aiKrseTdyQd/ul9W2BLu/embGa2mB56IT09OvHtF3s3qT6bRq7oE0jzV/yW62fkvvjRXM2ENxnySjpw4cewH/rY/fgnPkqYt0tzNzv3D1/eq6Jl/7cu/ryS1nvDyov1TYV56NcmJbHJ+5fadtXWvrxKW7t35CueVZ+bfPfPTNZZVJYRt2M4NTAT4nc7H/qE5T+WpPJWn8lSeylN5Kk/lqfwI5P8DeC6BGSvJ9J4AAAAASUVORK5CYII=" alt="Logo" height="40" class="me-2">
                     <span class="text-white fw-bold">Bắc Hà University</span> <!-- Optional text -->
                     </a>
					<!-- Nếu role là PDT thì hiển thị tất cả -->
					<c:if test="${role eq 'PDT'}">
						<li class="nav-item"><a
							class="nav-link <%= "user".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/user/user_view"> <i
								class="bi bi-person-gear me-1"></i> Quản lý tài khoản
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "major".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/major/major_view"> <i
								class="bi bi-diagram-3 me-1"></i> Chuyên Ngành
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "cohort".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/cohort/cohort_view">
								<i class="bi bi-calendar-range me-1"></i> Khóa Học
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "subject".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/subject/subject_view">
								<i class="bi bi-journal-bookmark me-1"></i> Môn Học
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "classes".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/classes/classes_view">
								<i class="bi bi-people-fill me-1"></i> Lớp
						</a></li>
						<li class="nav-item"><a
							class="nav-link <%= "subjectDetail".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/subjectDetail/subjectDetail_view">
								<i class="bi bi-card-list me-1"></i> Quản Lý Môn Học
						</a></li>


					</c:if>

					<!-- Nếu role là Teacher -->
					<c:if test="${role eq 'TEACHER'}">
						<li class="nav-item"><a
							class="nav-link <%= "subjectDetail".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/subjectDetail/subjectDetailByTeacher">SubjectDetail</a>
						</li>

					</c:if>

					<!-- Nếu role là Student -->
					<c:if test="${role eq 'STUDENT'}">
						<li class="nav-item"><a
							class="nav-link <%= "score".equals(pageActive) ? "active" : "" %>"
							href="${pageContext.request.contextPath}/score/scoreByStudent">Score</a>
						</li>
					</c:if>

				</ul>

				<div class="d-flex ms-auto align-items-center">

					<span class="text-white me-3"> Xin chào, <strong>${loggedInUser.username != null ? loggedInUser.username : "User"}
					</strong>
					</span> 
					<a class="btn btn-outline-light btn-sm"
						href="${pageContext.request.contextPath}/logout">Logout</a>
						
						<a class="btn btn-outline-light btn-sm"
						href="${pageContext.request.contextPath}/user/editInfo">Sửa thông tin</a>
				</div>

			</div>
		</div>
	</nav>

	<script>
    let accessToken = '<%=session.getAttribute("accessToken")%>';
</script>


	<script>
async function callApi(url, options = {}) {
    if (!options.headers) options.headers = {};
    options.headers['Authorization'] = 'Bearer ' + accessToken;

    let res = await fetch(url, options);

    // accessToken hết hạn
    if (res.status === 401) {
        const refreshRes = await fetch("/refresh-token", { method: 'POST' });
        if (refreshRes.ok) {
            accessToken = await refreshRes.text();
            options.headers['Authorization'] = 'Bearer ' + accessToken;
            return fetch(url, options); // retry call
        } else {
            alert("Phiên đăng nhập đã hết. Vui lòng đăng nhập lại!");
            window.location.href = "/login";
        }
    }

    return res;
}
</script>