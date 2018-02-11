public class Record {
	private int id;
	private String hostname;

	public int getId() {
		return id;
	}

	public String getHostname() {
		return hostname;
	}

	public int getDowntimeInMinutes() {
		return downtimeInMinutes;
	}

	public String getSeverity() {
		return severity;
	}

	public String getAttackType() {
		return attackType;
	}

	public int getSource() {
		return source;
	}

	public int getShift() {
		return shift;
	}

	private int downtimeInMinutes;
	private String severity;
	private String attackType;
	private int source;
	private int shift;

	public Record(int id,String hostname,int downtimeInMinutes,String severity,String attackType,int source,int shift) {
		this.id = id;
		this.hostname = hostname;
		this.downtimeInMinutes = downtimeInMinutes;
		this.severity = severity;
		this.attackType = attackType;
		this.source = source;
		this.shift = shift;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(id).append(";").append(hostname).append(";").append(downtimeInMinutes).append(";");
		stringBuilder.append(severity).append(";").append(attackType).append(";").append(source).append(";");
		stringBuilder.append(shift);
		return stringBuilder.toString();
	}
	public static Record build(String[] args){
		if(args.length < 7) return null;
		int id = Integer.parseInt(args[0]);
		String host = args[1];
		int down = Integer.parseInt(args[2]);
		String sev = args[3];
		String attack = args[4];
		int source = Integer.parseInt(args[5]);
		int shift = Integer.parseInt(args[6]);
		return new Record(id, host, down, sev, attack, source, shift);
	}
}