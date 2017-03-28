package main.java.net.zetetic;

public class UserEntity {

    private int gender;
    /** Not-null value. */
    private String pinyinName;
    /** Not-null value. */
    private String realName;
    /** Not-null value. */
    private String phone;
    /** Not-null value. */
    private String shortPhone;
    /** Not-null value. */

    protected Long id;
    protected int peerId;
    /** Not-null value.
     * userEntity --> nickName
     * groupEntity --> groupName
     * */
    protected String mainName;
    /** Not-null value.*/
    protected String avatar;
    protected int created;
    protected int updated;
    private int searchType;
    
    private int tempGroupRoleType;
    private String email;
    private int departmentId;
    private int status;
    private int msgUpdataTime;

    public UserEntity(Long id, int peerId, int gender, String mainName,
                      String pinyinName, String realName, String avatar,
                      String phone, String email, int departmentId,
                      int status, int created, int updated, String shortPhone) {
        this.id = id;
        this.peerId = peerId;
        this.gender = gender;
        this.mainName = mainName;
        this.pinyinName = pinyinName;
        this.realName = realName;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
        this.departmentId = departmentId;
        this.status = status;
        this.created = created;
        this.updated = updated;
        this.shortPhone = shortPhone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShortPhone() {
        return shortPhone;
    }

    public void setShortPhone(String shortPhone) {
        this.shortPhone = shortPhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeerId() {
        return peerId;
    }

    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public int getTempGroupRoleType() {
        return tempGroupRoleType;
    }

    public void setTempGroupRoleType(int tempGroupRoleType) {
        this.tempGroupRoleType = tempGroupRoleType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMsgUpdataTime() {
        return msgUpdataTime;
    }

    public void setMsgUpdataTime(int msgUpdataTime) {
        this.msgUpdataTime = msgUpdataTime;
    }
}
