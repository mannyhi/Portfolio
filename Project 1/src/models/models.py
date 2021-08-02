from json import JSONEncoder


# get name model
class Name:
    def __init__(self, ename):
        self.ename = ename


# JSON for Name
class UserEncoderName(JSONEncoder):
    def default(self, property):
        if isinstance(property, Name):
            return property.__dict__
        else:
            return super().default(self, property)


# employee view for pending request(s) model
class EVP:
    def __init__(self, rid, ramount, reason, rtime, rstatus):
        self.rid = rid
        self.ramount = '${:,.2f}'.format(ramount)
        self.reason = reason
        self.rtime = str(rtime)
        self.rstatus = rstatus


# JSON for EVP
class UserEncoderEVP(JSONEncoder):
    def default(self, property):
        if isinstance(property, EVP):
            return property.__dict__
        else:
            return super().default(self, property)


# employee view for history of request(s) made
class EVH:
    def __init__(self, rid, ramount, reason, rtime, rstatus, rmessage, ctime, cname):
        self.rid = rid
        self.ramount = '${:,.2f}'.format(ramount)
        self.reason = reason
        self.rtime = str(rtime)
        self.rstatus = rstatus
        self.rmessage = rmessage
        self.ctime = str(ctime)
        self.cname = cname


# JSON for EVH
class UserEncoderEVH(JSONEncoder):
    def default(self, property):
        if isinstance(property, EVH):
            return property.__dict__
        else:
            return super().default(self, property)


# manager view for pending request(s) model
class MVP:
    def __init__(self, rid, ename, eid, ramount, reason, rtime, rstatus):
        self.rid = rid
        self.ename = ename
        self.eid = eid
        self.ramount = '${:,.2f}'.format(ramount)
        self.reason = reason
        self.rtime = str(rtime)
        self.rstatus = rstatus


# JSON for MVP
class UserEncoderMVP(JSONEncoder):
    def default(self, property):
        if isinstance(property, MVP):
            return property.__dict__
        else:
            return super().default(self, property)


# manager view for history of request(s) made model
class MVH:
    def __init__(self, rid, ename, eid, ramount, reason, rtime, rstatus, rmessage, ctime, cname):
        self.rid = rid
        self.ename = ename
        self.eid = eid
        self.ramount = '${:,.2f}'.format(ramount)
        self.reason = reason
        self.rtime = str(rtime)
        self.rstatus = rstatus
        self.rmessage = rmessage
        self.ctime = str(ctime)
        self.cname = cname


# JSON for MVH
class UserEncoderMVH(JSONEncoder):
    def default(self, property):
        if isinstance(property, MVH):
            return property.__dict__
        else:
            return super().default(self, property)


# employee view for pending request(s) model
class Stats:
    def __init__(self, total, approved, denied, pending, mean):
        self.total = total
        self.approved = approved
        self.denied = denied
        self.pending = pending
        self.mean = '${:,.2f}'.format(mean)


# JSON for Stats
class UserEncoderStats(JSONEncoder):
    def default(self, property):
        if isinstance(property, Stats):
            return property.__dict__
        else:
            return super().default(self, property)


# big spender model
class BG:
    def __init__(self, ename, money):
        self.ename = ename
        self.money = '${:,.2f}'.format(money)


# JSON for BG
class UserEncoderBG(JSONEncoder):
    def default(self, property):
        if isinstance(property, BG):
            return property.__dict__
        else:
            return super().default(self, property)
