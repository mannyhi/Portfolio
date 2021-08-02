# Controller layer responsible for views and interacts with models and presentation/data
from src.models.models import *
import src.services.services as sr
import src.dao.dao as dao
from src.app import flask_app
from flask import request, Response, redirect, session, jsonify
import werkzeug.exceptions
from json import dumps

import logging

logging.basicConfig(filename='diagnostic.log', level=logging.INFO)


# endpoint to check credentials
@flask_app.route('/login', methods=['POST'])
def login():
    logging.info('controllers: login')
    username = request.form.get('username')
    password = request.form.get('password')
    if sr.login_check(username, password):
        session["uid"] = username
        if username[0:5] == 'admin':
            return redirect('../ersm.html')
        else:
            return redirect('../erse.html')
    else:
        session["uid"] = 'invalid'
        return redirect('../ers.html')


# endpoint to assist display invalid login on webpage
@flask_app.route('/invalid', methods=['GET'])
def invalid():
    logging.info('controllers: invalid')
    if "uid" in session:
        if session["uid"] == 'invalid':
            session.clear()
            return jsonify('invalid')
    return jsonify('reset')


# endpoint to logout
@flask_app.route('/logout', methods=['GET'])
def logout():
    logging.info('controllers: logout')
    session.clear()
    return redirect('../ers.html')


# endpoint to GET name
@flask_app.route('/name', methods=['GET'])
def name():
    logging.info('controllers: name')
    if "uid" in session:
        my_json = dumps(sr.name(session["uid"]), cls=UserEncoderName)
        return Response(my_json)
    return jsonify('none')


# endpoint to create new request
@flask_app.route('/cr', methods=['POST'])  # needs to be POST
def create_request():
    logging.info('controllers: create request')
    if "uid" in session and session["uid"][0:5] != 'admin':
        sr.create_request(session["uid"], request.form.get('Amount'), request.form.get('Reason'))
    return redirect('../erse.html')


# endpoint for managerial approval or denial of request
@flask_app.route('/mu', methods=['POST'])
def manager_update():
    content = request.get_json()
    logging.info('controllers: manager update')
    if "uid" in session and session["uid"][0:5] == 'admin':
        sr.manager_update(content['rid'], content['rstatus'], content['rmessage'], dao.name(session["uid"]))
    return jsonify('none')


# endpoint to GET employee view of pending request(s)
@flask_app.route('/evp', methods=['GET'])
def evp():
    logging.info('controllers: evp')
    if "uid" in session and session["uid"][0:5] != 'admin':
        my_json = dumps(sr.evp(session["uid"]), cls=UserEncoderEVP)
        return Response(my_json)
    return jsonify('none')


# endpoint to GET employee view of history of request(s) made
@flask_app.route('/evh', methods=['GET'])
def evh():
    logging.info('controllers: evh')
    if "uid" in session and session["uid"][0:5] != 'admin':
        my_json = dumps(sr.evh(session["uid"]), cls=UserEncoderEVH)
        return Response(my_json)
    return jsonify('none')


# endpoint to GET statistical information on reimbursements
@flask_app.route('/stats', methods=['GET'])
def stats():
    logging.info('controllers: stats')
    if "uid" in session and session["uid"][0:5] == 'admin':
        my_json = dumps(sr.stats(), cls=UserEncoderStats)
        return Response(my_json)
    return jsonify('none')


# endpoint to provide big spender information
@flask_app.route('/bg', methods=['GET'])
def bg():
    logging.info('controllers: bg')
    if "uid" in session and session["uid"][0:5] == 'admin':
        my_json = dumps(sr.bg(), cls=UserEncoderBG)
        return Response(my_json)
    return jsonify('none')


# endpoint to GET manager view of pending request(s)
@flask_app.route('/mvp', methods=['GET'])
def mvp():
    logging.info('controllers: mvp')
    if "uid" in session and session["uid"][0:5] == 'admin':
        my_json = dumps(sr.mvp(), cls=UserEncoderMVP)
        return Response(my_json)
    return jsonify('none')


# endpoint to GET manager view of history of request(s) made
@flask_app.route('/mvh', methods=['GET'])
def mvh():
    logging.info('controllers: mvh')
    if "uid" in session and session["uid"][0:5] == 'admin':
        my_json = dumps(sr.mvh(), cls=UserEncoderMVH)
        return Response(my_json)
    return jsonify('none')


# endpoint to handle exception when reaching an unknown resource
@flask_app.errorhandler(werkzeug.exceptions.NotFound)
def handle_404(error):
    logging.info('controllers: errorhandler')
    return '404 Not Found: No such resource exists on this server', 404
